package com.mintlify.document.ui;

import com.intellij.openapi.actionSystem.ActionManager;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.ex.ActionUtil;
import com.intellij.openapi.actionSystem.ActionPlaces;

import com.intellij.openapi.wm.ToolWindow;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

public class MyToolWindow {

  private JPanel myToolWindowContent;
  private JComboBox<String> docFormatSelector;
  private JButton generateDocsButton;
  private JButton joinCommunityLabel;

  public MyToolWindow(ToolWindow toolWindow) {
    docFormatSelector.addItem("Auto-detect");
    docFormatSelector.addItem("Javadoc");
    docFormatSelector.addItem("Google");
    docFormatSelector.addItem("JSDoc");
    docFormatSelector.addItem("reST");
    docFormatSelector.addItem("NumPy");
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

    try {
      final URI joinDiscordUri = new URI("https://discord.gg/6W7GuYuxra");
      joinCommunityLabel.setBorderPainted(false);
      joinCommunityLabel.setOpaque(false);
      joinCommunityLabel.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
          if (Desktop.isDesktopSupported()) {
            try {
              Desktop.getDesktop().browse(joinDiscordUri);
            } catch (IOException err) { /* TODO: error handling */ }
          } else { /* TODO: error handling */ }
        }
      });
    } catch (URISyntaxException err) {
      /* TODO: error handling */
    }
  }
  public String getSelectedDocFormat() {
    return (String) docFormatSelector.getSelectedItem();
  }

  public JPanel getContent() {
    return myToolWindowContent;
  }
}
