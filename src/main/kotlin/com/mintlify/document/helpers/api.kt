package com.mintlify.document.helpers

import com.github.kittinunf.fuel.core.extensions.jsonBody
import com.github.kittinunf.fuel.httpPost
import com.google.gson.Gson
import com.beust.klaxon.Klaxon
import com.intellij.util.SystemProperties

data class RequestBody(
    var userId: String,
    var code: String,
    var languageId: String?,
    var context: String,
    var width: Int,
    var commented: Boolean,
    var email: String,
    var docStyle: String,
    var source: String,
    // TODO: Add location
)

data class Response(
    var docstring: String,
    var position: String,
)

fun getDocFromApi(
    code: String,
    languageId: String?,
    context: String = code,
    width: Int = 80,
    commented: Boolean = true,
    email: String = "",
    docStyle: String = "Auto-detect",
): Response? {
    val source = "intellij"
    val userId = System.getProperty("user.name")
    val body = RequestBody(userId, code, languageId, context, width, commented, email, docStyle, source);
    val (_, _, result) = "http://localhost:5000/docs/write/v2".httpPost()
        .jsonBody(Gson().toJson(body).toString())
        .responseString()
    val (payload, _) = result
    if (payload != null) {
        return Klaxon().parse<Response>(payload)
    }

    return null;
}