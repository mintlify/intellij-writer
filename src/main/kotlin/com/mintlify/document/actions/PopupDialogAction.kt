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
import java.awt.event.ActionEvent

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
                var whitespaceBeforeLine = getWhitespaceOfLineAtOffset(document, startLineNumber)

                val languageId = e.getData(LangDataKeys.PSI_FILE)?.language?.displayName?.lowercase()
                val width = editor.settings.getRightMargin(project) - whitespaceBeforeLine.length
                val response = getDocFromApi(selectedText, "testingID", languageId, documentText, width)
                indicator.fraction = 1.0
                if (response != null) {
                    val isBelowStartLine = response.position === "belowStartLine";
                    val insertPosition = if (isBelowStartLine) document.getLineStartOffset(startLineNumber + 1) else start
                    val insertDoc = getFormattedInsertDoc(response.docstring, whitespaceBeforeLine, isBelowStartLine)

                    WriteCommandAction.runWriteCommandAction(project) {
                        document.insertString(insertPosition, insertDoc)
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

fun getWhitespaceOfLineAtOffset(document: Document, lineNumber: Int): String {
    val documentText = document.text

    val startLineStartOffset = document.getLineStartOffset(lineNumber)
    val startLineEndOffset = document.getLineEndOffset(lineNumber)
    val startLine = documentText.substring(startLineStartOffset, startLineEndOffset)
    return getWhitespaceSpaceBefore(startLine)
}

fun getFormattedInsertDoc(docstring: String, whitespaceBeforeLine: String, isBelowStartLine: Boolean = false): String {
    var differingWhitespaceBeforeLine = whitespaceBeforeLine
    var lastLineWhitespace = ""
    // Format for tabbed position
    if (isBelowStartLine) {
        differingWhitespaceBeforeLine = '\t' + differingWhitespaceBeforeLine
    } else {
        lastLineWhitespace = differingWhitespaceBeforeLine
    }
    val docstringByLines = docstring.lines().mapIndexed { index, line -> (
        if (index == 0 && !isBelowStartLine) {
            line
        } else {
            differingWhitespaceBeforeLine + line
        })
    }
    return docstringByLines.joinToString("\n") + '\n' + lastLineWhitespace
}