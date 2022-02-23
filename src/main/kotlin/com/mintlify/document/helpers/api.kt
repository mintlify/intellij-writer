package com.mintlify.document.helpers

import com.github.kittinunf.fuel.core.extensions.jsonBody
import com.github.kittinunf.fuel.httpPost
import com.google.gson.Gson
import com.beust.klaxon.Klaxon

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
    // For no-selection
    var location: Int,
    var line: String,
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
    location: Int,
    line: String,
): Response? {
    val source = "intellij"
    val userId = System.getProperty("user.name")
    val body = RequestBody(userId, code, languageId, context, width, commented, email, docStyle, source, location, line)

    val apiBase = "https://api.mintlify.com/docs/"
    var endpoint = apiBase + "write/v2"
    if (code.isEmpty()) {
        endpoint = apiBase + "write/v2/no-selection"
    }

    val (_, _, result) = endpoint.httpPost()
        .jsonBody(Gson().toJson(body).toString())
        .responseString()
    val (payload, _) = result
    if (payload != null) {
        return Klaxon().parse<Response>(payload)
    }

    return null
}