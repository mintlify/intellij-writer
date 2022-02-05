package com.mintlify.document.actions;

import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.actionSystem.CommonDataKeys
import com.intellij.openapi.editor.CaretModel
import com.intellij.openapi.editor.Editor

import com.mintlify.document.helpers.getDocFromApi

public class PopupDialogAction : AnAction() {
    override fun actionPerformed(e: AnActionEvent) {
        val editor: Editor = e.getRequiredData(CommonDataKeys.EDITOR)
        val caretModel: CaretModel = editor.getCaretModel()
        val selectedText = caretModel.currentCaret.selectedText ?: "";

        val docstring = getDocFromApi(selectedText, "testingID");
        println(docstring);
    }
}