package com.mintlify.document.actions

import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.actionSystem.CommonDataKeys
import com.intellij.openapi.actionSystem.LangDataKeys
import com.intellij.openapi.command.WriteCommandAction
import com.intellij.openapi.editor.CaretModel
import com.intellij.openapi.editor.Editor
import com.intellij.openapi.editor.Document
import com.intellij.openapi.progress.ProgressIndicator
import com.intellij.openapi.project.Project
import com.intellij.openapi.progress.ProgressManager
import com.intellij.openapi.progress.Task

import com.mintlify.document.helpers.getDocFromApi

public class PopupDialogAction : AnAction() {
    override fun actionPerformed(e: AnActionEvent) {
        val editor: Editor = e.getRequiredData(CommonDataKeys.EDITOR)
        val project: Project = e.getRequiredData(CommonDataKeys.PROJECT)
        val document: Document = editor.document

        val task = object : Task.Backgroundable(project, "AI doc writer progress") {
            override fun run(indicator: ProgressIndicator) {
                indicator.text = "Generating docs"
                // TODO: Update with moving progress bar
                indicator.fraction = 0.8
                val caretModel: CaretModel = editor.caretModel
                val selectedText = caretModel.currentCaret.selectedText?.trim() ?: ""
                val selectionStart = caretModel.currentCaret.selectionStart
                val documentText = document.text
                val start = documentText.indexOf(selectedText, selectionStart)

                // Get space before start line
                val startLineNumber = document.getLineNumber(start)
                val startLineStartOffset = document.getLineStartOffset(startLineNumber)
                val startLineEndOffset = document.getLineEndOffset(startLineNumber)
                val startLine = documentText.substring(startLineStartOffset, startLineEndOffset)
                val whitespaceBeforeLine = getWhitespaceSpaceBefore(startLine)

                val languageId = e.getData(LangDataKeys.PSI_FILE)?.language?.displayName?.lowercase()
                val width = editor.settings.getRightMargin(project) - whitespaceBeforeLine.length;
                val response = getDocFromApi(selectedText, "testingID", languageId, documentText, width)
                indicator.fraction = 1.0
                if (response != null) {
                    // Insert docstring
                    val docstringByLines = response.docstring.lines().mapIndexed { index, line -> (
                        if (index == 0) {
                            line
                        } else {
                            whitespaceBeforeLine + line
                        }
                      )
                    }
                    val insertString = docstringByLines.joinToString("\n") + '\n' + whitespaceBeforeLine
                    WriteCommandAction.runWriteCommandAction(project) {
                        document.insertString(start, insertString)
                    }
                }
            }
        }
        ProgressManager.getInstance().run(task)
    }
}

fun getWhitespaceSpaceBefore(text: String): String {
    val frontWhiteSpaceRemoved = text.trimStart()
    val firstNoneWhiteSpaceIndex = text.indexOf(frontWhiteSpaceRemoved)
    return text.substring(0, firstNoneWhiteSpaceIndex)
}