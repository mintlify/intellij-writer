package com.mintlify.document.events

import com.intellij.util.messages.Topic

interface SnykResultsFilteringListener {
    companion object {
        val SNYK_FILTERING_TOPIC =
            Topic.create("Documentation format", SnykResultsFilteringListener::class.java)
    }

    fun filtersChanged()

}
