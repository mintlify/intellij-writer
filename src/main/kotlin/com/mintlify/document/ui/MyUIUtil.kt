package com.mintlify.document.ui

import com.intellij.openapi.wm.IdeFocusManager
import javax.swing.*

object MyUIUtil {
  fun focusPanel(panel: JComponent) {
    val focusManager = IdeFocusManager.findInstanceByComponent(panel)
    val toFocus = focusManager.getFocusTargetFor(panel) ?: return
    focusManager.doWhenFocusSettlesDown { focusManager.requestFocus(toFocus, true) }
  }
}