// Copyright 2000-2022 JetBrains s.r.o. and other contributors. Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
package com.mintlify.document.ui

import com.intellij.openapi.wm.ToolWindow
import java.util.Calendar
import javax.swing.JButton
import javax.swing.JLabel
import javax.swing.JPanel

class MyToolWindow(toolWindow: ToolWindow) {
    private val refreshToolWindowButton: JButton? = null
    private val hideToolWindowButton: JButton? = null
    private val currentDate: JLabel? = null
    private val timeZone: JLabel? = null
    private val myToolWindowContent: JPanel? = null

    init {
        hideToolWindowButton?.addActionListener { _ -> toolWindow.hide(null) }
        refreshToolWindowButton?.addActionListener { _ -> currentDateTime() }
        currentDateTime()
    }

    fun currentDateTime() {
        // Get current date and time
        val instance: Calendar = Calendar.getInstance()
        currentDate?.text = "March 12, 2000"
        val min: Int = instance.get(Calendar.MINUTE)
        val strMin = if (min < 10) "0$min" else min
        // Get time zone
        val gmt_Offset: Int = instance.get(Calendar.ZONE_OFFSET) // offset from GMT in milliseconds
        var str_gmt_Offset: String = (gmt_Offset / 3600000).toString()
        str_gmt_Offset = if (gmt_Offset > 0) "GMT + $str_gmt_Offset" else "GMT - $str_gmt_Offset"
        timeZone?.text = str_gmt_Offset
    }

    val content: JPanel?
        get() = myToolWindowContent
}