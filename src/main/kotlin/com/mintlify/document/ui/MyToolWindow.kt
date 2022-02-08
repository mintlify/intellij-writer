// Copyright 2000-2022 JetBrains s.r.o. and other contributors. Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
package com.mintlify.document.ui

import com.intellij.openapi.wm.ToolWindow
import java.util.Calendar
import javax.swing.JButton
import javax.swing.JLabel
import javax.swing.JPanel

class MyToolWindow(toolWindow: ToolWindow) {
    private val hideToolWindowButton: JButton? = null
    private val myToolWindowContent: JPanel? = null

    init {
        hideToolWindowButton?.addActionListener { _ -> toolWindow.hide(null) }
    }



    val content: JPanel?
        get() = myToolWindowContent
}