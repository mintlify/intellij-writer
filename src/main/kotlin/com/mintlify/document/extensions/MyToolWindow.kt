// Copyright 2000-2022 JetBrains s.r.o. and other contributors. Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
package com.mintlify.document.extensions

import com.intellij.openapi.wm.ToolWindow
import java.util.Calendar
import javax.swing.ImageIcon
import javax.swing.JButton
import javax.swing.JLabel
import javax.swing.JPanel

class MyToolWindow(toolWindow: ToolWindow) {
    private val refreshToolWindowButton: JButton? = null
    private val hideToolWindowButton: JButton? = null
    private val currentDate: JLabel? = null
    private val currentTime: JLabel? = null
    private val timeZone: JLabel? = null
    private val myToolWindowContent: JPanel? = null

    init {
        hideToolWindowButton?.addActionListener { e -> toolWindow.hide(null) }
        refreshToolWindowButton?.addActionListener { e -> currentDateTime() }
        currentDateTime()
    }

    fun currentDateTime() {
        // Get current date and time
        val instance: Calendar = Calendar.getInstance()
        currentDate?.setText(
            instance.get(Calendar.DAY_OF_MONTH) + "/"
                    + (instance.get(Calendar.MONTH) + 1) + "/"
                    + instance.get(Calendar.YEAR)
        )
        currentDate.setIcon(ImageIcon(getClass().getResource("/toolWindow/Calendar-icon.png")))
        val min: Int = instance.get(Calendar.MINUTE)
        val strMin = if (min < 10) "0$min" else String.valueOf(min)
        currentTime?.setText(instance.get(Calendar.HOUR_OF_DAY) + ":" + strMin)
        currentTime?.setIcon(ImageIcon(getClass().getResource("/toolWindow/Time-icon.png")))
        // Get time zone
        val gmt_Offset: Int = instance.get(Calendar.ZONE_OFFSET) // offset from GMT in milliseconds
        var str_gmt_Offset: String = String.valueOf(gmt_Offset / 3600000)
        str_gmt_Offset = if (gmt_Offset > 0) "GMT + $str_gmt_Offset" else "GMT - $str_gmt_Offset"
        timeZone.setText(str_gmt_Offset)
        timeZone.setIcon(ImageIcon(getClass().getResource("/toolWindow/Time-zone-icon.png")))
    }

    val content: JPanel?
        get() = myToolWindowContent
}