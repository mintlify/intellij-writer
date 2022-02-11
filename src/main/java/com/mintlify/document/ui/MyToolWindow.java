package com.mintlify.document.ui;

import com.intellij.openapi.actionSystem.ActionManager;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.ex.ActionUtil;
import com.intellij.openapi.wm.ToolWindow;
import com.intellij.openapi.actionSystem.ActionPlaces;
import com.mintlify.document.actions.PopupDialogAction;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MyToolWindow {

  private JPanel myToolWindowContent;
  private JComboBox<String> docFormatSelector;
  private JButton generateDocsButton;

  public MyToolWindow(ToolWindow toolWindow) {
    docFormatSelector.setEditable(false);
    docFormatSelector.addItem("Auto-detect");
    docFormatSelector.addItem("Javadoc");
    generateDocsButton.addActionListener(new MyActionListener());
  }


  public JPanel getContent() {
    return myToolWindowContent;
  }

  private static class MyActionListener implements ActionListener {
    public void actionPerformed(ActionEvent e)
    {
      ActionManager actionManager = ActionManager.getInstance();
      AnAction action = actionManager.getAction("org.intellij.sdk.action.PopupDialogAction");
      ActionUtil.invokeAction(action, (Component) e.getSource(), ActionPlaces.TOOLWINDOW_CONTENT, null, null);
    }
  }
}
