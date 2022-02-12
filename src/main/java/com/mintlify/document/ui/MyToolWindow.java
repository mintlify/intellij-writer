package com.mintlify.document.ui;

import com.intellij.openapi.actionSystem.ActionManager;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.ex.ActionUtil;
import com.intellij.openapi.actionSystem.ActionPlaces;

import com.intellij.openapi.ui.ComboBox;
import com.intellij.openapi.wm.ToolWindow;



import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MyToolWindow {

  private JPanel myToolWindowContent;
  private JComboBox<String> docFormatSelector;
  private JButton generateDocsButton;

  public MyToolWindow(ToolWindow toolWindow) {
    docFormatSelector.addItem("Auto-detect");
    docFormatSelector.addItem("Javadoc");
    docFormatSelector.addItem("Google");
    docFormatSelector.addItem("JSDoc");
    docFormatSelector.addItem("reST");
    docFormatSelector.addItem("DocBlock");

    docFormatSelector.setEditable(false);
    generateDocsButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        ActionManager actionManager = ActionManager.getInstance();
        AnAction action = actionManager.getAction("org.intellij.sdk.action.PopupDialogAction");
        ActionUtil.invokeAction(action, toolWindow.getComponent(), ActionPlaces.TOOLWINDOW_CONTENT, null, null);
      }
    });
  }
  public String getSelectedDocFormat() {
    return (String) docFormatSelector.getSelectedItem();
  }

  public JPanel getContent() {
    return myToolWindowContent;
  }
}
