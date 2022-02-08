package com.mintlify.document.ui

import com.intellij.openapi.project.Project
import com.intellij.openapi.Disposable
import com.intellij.openapi.util.Disposer
import com.intellij.ui.content.Content
import com.intellij.util.ui.UIUtil
import com.mintlify.document.MyBundle
import com.mintlify.document.ui.MyUIUtil
import java.awt.BorderLayout
import kotlin.properties.Delegates

internal class MintlifyToolWindowTabControllerImpl(private val project: Project,
                                                   private val tab: Content): MintlifyToolWindowTabController {

  private val mainPanel = tab.component.apply {
    layout = BorderLayout()
    background = UIUtil.getListBackground()
  }

  private var contentDisposable by Delegates.observable<Disposable?>(null) { _, oldValue, newValue ->
    if (oldValue != null) Disposer.dispose(oldValue)
    if (newValue != null) Disposer.register(tab.disposer!!, newValue)
  }

  private var showingSelectors: Boolean? = null

  init {
    showSelectors()
  }

  private fun showSelectors() {
    if (showingSelectors == true) return
    val disposable = Disposer.newDisposable()
    contentDisposable = disposable
    tab.displayName = MyBundle.message("toolwindow.AI_Doc_Writer")

    val component = DocFormatSelectorComponentFactory(project).create(disposable) { _ ->
      MyUIUtil.focusPanel(mainPanel)
    }
    with(mainPanel) {
      removeAll()
      add(component, BorderLayout.NORTH)
      revalidate()
      repaint()
    }
    showingSelectors = true
  }
}