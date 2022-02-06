@file:JvmName("UtilsKt")

package com.mintlify.document

import com.intellij.openapi.project.Project
import com.intellij.openapi.components.service
import com.mintlify.document.services.SnykApplicationSettingsStateService
import java.util.Objects.nonNull

fun pluginSettings(): SnykApplicationSettingsStateService = service()

fun isProjectSettingsAvailable(project: Project?) = nonNull(project) && !project!!.isDefault