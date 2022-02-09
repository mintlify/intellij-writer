package com.mintlify.document.ui;

import com.intellij.openapi.wm.ToolWindow;
// import com.mintlify.document.actions.PopupDialogAction;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

public class MyToolWindow {

  private JButton generateDocsButton;
  private JPanel myToolWindowContent;
  private JComboBox<String> docFormatSelector;

  public MyToolWindow(ToolWindow toolWindow) {
    // TODO
    // generateDocsButton.addActionListener(e -> PopupDialogAction.ACTIONS_KEY);
    docFormatSelector.setEditable(false);
    docFormatSelector.addItem("Auto-detect");
    docFormatSelector.addItem("Javadoc");
  }

  public JPanel getContent() {
    return myToolWindowContent;
  }
}
