package com.mintlify.document.actions;

import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.actionSystem.CommonDataKeys
import com.intellij.openapi.actionSystem.LangDataKeys
import com.intellij.openapi.command.WriteCommandAction
import com.intellij.openapi.editor.CaretModel
import com.intellij.openapi.editor.Editor
import com.intellij.openapi.editor.Document
import com.intellij.openapi.project.Project
import com.mintlify.document.helpers.getDocFromApi


public class PopupDialogAction : AnAction() {
    override fun actionPerformed(e: AnActionEvent) {
        val editor: Editor = e.getRequiredData(CommonDataKeys.EDITOR)
        val project: Project = e.getRequiredData(CommonDataKeys.PROJECT)
        val document: Document = editor.document

        val caretModel: CaretModel = editor.getCaretModel()
        val selectedText = caretModel.currentCaret.selectedText ?: "";
        val start = caretModel.currentCaret.selectionStart;

        val languageId = e.getData(LangDataKeys.PSI_FILE)?.language?.displayName?.lowercase();
        val context = document.text;

        val response = getDocFromApi(selectedText, "testingID", languageId, context);
        if (response != null) {
            val insertDocstring = response.docstring + '\n';
            WriteCommandAction.runWriteCommandAction(project) {
                document.replaceString(start, start, insertDocstring)
            }
        }
    }
}