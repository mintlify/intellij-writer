package com.mintlify.document.ui

internal class MintlifyToolWindowTabControllerImpl(private val project: Project,
                                                            private val tab: Content): MintlifyToolWindowTabController {
  private var showingSelectors: Boolean? = null

  private fun showSelectors() {
    if (showingSelectors == true) return
    val disposable = Disposer.newDisposable()
    contentDisposable = disposable
    tab.displayName = MyBundle.message("toolwindow.AI_Doc_Writer")

    val component = GHPRRepositorySelectorComponentFactory(project).create(disposable) { _ ->
      GHUIUtil.focusPanel(mainPanel)
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