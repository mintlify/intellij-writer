package com.mintlify.document.ui

import com.intellij.ide.plugins.newui.HorizontalLayout
import com.intellij.openapi.project.Project
import com.intellij.openapi.Disposable
import com.intellij.openapi.util.Disposer
import com.intellij.openapi.ui.ComboBox
import com.intellij.util.ui.JBUI
import com.intellij.util.ui.UI
import com.mintlify.document.MyBundle
import net.miginfocom.layout.CC
import net.miginfocom.layout.LC
import net.miginfocom.layout.PlatformDefaults
import net.miginfocom.swing.MigLayout
import java.awt.event.ActionEvent
import javax.swing.*

class DocFormatSelectorComponentFactory(private val project: Project) {
  fun create(disposable: Disposable, onSelected: (String) -> Unit): JComponent {
    // Dropdown
      val formatOptions = arrayOf<String>("Auto-detect", "JSDoc", "reST",
          "DocBlock", "JavaDoc", "Google")
    val docFormatDropdown: ComboBox<String> = ComboBox<String>(formatOptions)
      docFormatDropdown.isEditable = false

    val applyAction = object : AbstractAction(MyBundle.message("doc.format")) {
      override fun actionPerformed(e: ActionEvent?) {
        val format: String = (docFormatDropdown.selectedItem ?: return) as String
        onSelected(format)
      }
    }

    val applyButton = JButton(applyAction).apply {
      isOpaque = false
      controlVisibilityFromAction(this, applyAction)
    }
    
    // Button
    val generateDocsAction = object : AbstractAction(MyBundle.message("action.Document.text")) {
      override fun actionPerformed(e: ActionEvent?) {
        // TODO : Call API
      }
    }

    val ctrl = Controller(project, applyAction, generateDocsAction)
    Disposer.register(disposable, ctrl)

    val generateDocsButton = JButton(generateDocsAction).apply {
      isOpaque = false
      controlVisibilityFromAction(this, generateDocsAction)
    }

    val actionsPanel = JPanel(HorizontalLayout(UI.scale(16))).apply {
      isOpaque = false
      add(generateDocsButton)
      putClientProperty(PlatformDefaults.VISUAL_PADDING_PROPERTY, applyButton.insets)
    }

    return JPanel(null).apply {
      isOpaque = false
      border = JBUI.Borders.empty(30, 16)
      layout = MigLayout(LC().fill().gridGap("${UI.scale(10)}px", "${UI.scale(16)}px").insets("0").hideMode(3).noGrid())

      add(docFormatDropdown, CC().growX().push())

      add(actionsPanel, CC().newline())
    }
  }

  private class Controller(private val project: Project,
                           private val applyAction: Action,
                           private val generateDocsActon: Action) : Disposable {
    override fun dispose() {
    }
  }

  companion object {
    private const val ACTION_VISIBLE_KEY = "ACTION_VISIBLE"

    private fun controlVisibilityFromAction(button: JButton, action: Action) {
      fun update() {
        button.isVisible = action.getValue(ACTION_VISIBLE_KEY) as? Boolean ?: true
      }
      action.addPropertyChangeListener {
        update()
      }
      update()
    }
  }
}