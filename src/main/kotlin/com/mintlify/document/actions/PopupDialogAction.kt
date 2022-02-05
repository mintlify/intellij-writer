package com.mintlify.document.actions;

import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.actionSystem.CommonDataKeys
import com.intellij.openapi.editor.CaretModel
import com.intellij.openapi.editor.Editor

import com.github.kittinunf.fuel.core.extensions.jsonBody
import com.github.kittinunf.fuel.httpPost
import com.google.gson.Gson


/*languageId,
        commented: true,
        userId: vscode.env.machineId,
        email: authService.getEmail(),
        docStyle: getDocStyleConfig(),
        source: 'vscode',
        context: getText(),
        width: line ? getWidth(line.firstNonWhitespaceCharacterIndex) : getWidth(selection.start.character),
        // code to use for selected
        code: highlighted,*/
data class RequestBody(
    var userId: String,
    var code: String,
    var context: String = code,
    var commented: Boolean = true,
    var email: String = "",
    var docStyle: String = "Auto-detect",
    var source: String = "intellij",
    var width: Int = 80
    )

public class PopupDialogAction : AnAction() {
    override fun actionPerformed(e: AnActionEvent) {
        val editor: Editor = e.getRequiredData(CommonDataKeys.EDITOR)
        val caretModel: CaretModel = editor.getCaretModel()
        val selectedText = caretModel.currentCaret.selectedText ?: "";

        val body = RequestBody("test123", selectedText);

        val (_, _, result) = "http://localhost:5000/docs/write/v2".httpPost()
            .jsonBody(Gson().toJson(body).toString())
            .responseString()
        println(result)
    }
}