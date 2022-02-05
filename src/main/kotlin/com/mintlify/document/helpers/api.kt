package com.mintlify.document.helpers

import com.github.kittinunf.fuel.core.extensions.jsonBody
import com.github.kittinunf.fuel.httpPost
import com.google.gson.Gson

data class RequestBody(
    var userId: String,
    var code: String,
    var context: String,
    var commented: Boolean,
    var email: String,
    var docStyle: String,
    var source: String,
    var width: Int,
    // TODO: Add location
)

fun getDocFromApi(
    code: String,
    userId: String,
    context: String = code,
    commented: Boolean = true,
    email: String = "",
    docStyle: String = "Auto-detect",
    source: String = "intellij",
    width: Int = 80
): String? {

    val body = RequestBody(userId, code, context, commented, email, docStyle, source, width);
    val (_, _, result) = "http://localhost:5000/docs/write/v2".httpPost()
        .jsonBody(Gson().toJson(body).toString())
        .responseString()
    val (payload, _) = result
    if (payload != null) {
        return payload;
    }

    return null;
}