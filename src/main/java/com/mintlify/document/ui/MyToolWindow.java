package com.mintlify.document.ui;

import com.intellij.openapi.actionSystem.ActionManager;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.ex.ActionUtil;
import com.intellij.openapi.actionSystem.ActionPlaces;
import com.intellij.openapi.module.Module;
import com.intellij.openapi.module.ModuleManager;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.wm.ToolWindow;

import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MyToolWindow {

  private JPanel myToolWindowContent;
  private JComboBox<String> docFormatSelector;
  private JButton generateDocsButton;

  public MyToolWindow(ToolWindow toolWindow, Project project) {
    docFormatSelector.setEditable(false);
    docFormatSelector.addItem("Auto-detect");
    docFormatSelector.addItem("Javadoc");
    generateDocsButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        ActionManager actionManager = ActionManager.getInstance();
        AnAction action = actionManager.getAction("org.intellij.sdk.action.PopupDialogAction");
        ActionUtil.invokeAction(action, toolWindow.getComponent(), ActionPlaces.TOOLWINDOW_CONTENT, null, null);
      }
    });
  }

  public JPanel getContent() {
    return myToolWindowContent;
  }
}
