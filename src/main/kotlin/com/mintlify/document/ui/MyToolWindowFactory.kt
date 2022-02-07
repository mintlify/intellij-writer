// Copyright 2000-2022 JetBrains s.r.o. and other contributors. Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
package com.mintlify.document.ui

import com.intellij.openapi.project.Project
import com.intellij.openapi.wm.ToolWindow
import com.intellij.openapi.wm.ToolWindowFactory
import com.intellij.ui.content.Content
import com.intellij.ui.content.ContentFactory
import com.intellij.openapi.project.DumbAware
import com.intellij.openapi.util.Disposer
import javax.swing.JPanel

class MyToolWindowFactory : ToolWindowFactory, DumbAware {

    override fun createToolWindowContent(project: Project, toolWindow: ToolWindow) = with(toolWindow as ToolWindowEx) {
        with(contentManager) {
            addContent(factory.createContent(JPanel(null), null, false).apply {
                isCloseable = false
                setDisposer(Disposer.newDisposable("AI Doc Writer tab disposable"))
            }.also {
                it.putUserData(GHPRToolWindowTabController.KEY,
                            GHPRToolWindowTabControllerImpl(project, authManager, repositoryManager, dataContextRepository, projectString, it))
            })
        }
    }

    override fun shouldBeAvailable(project: Project): Boolean = false

    companion object {
        const val ID = "AI Doc Writer"
    }
}