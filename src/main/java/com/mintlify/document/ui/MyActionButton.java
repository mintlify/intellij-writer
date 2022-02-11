package com.mintlify.document.ui;

import com.intellij.ide.DataManager;
import com.intellij.openapi.actionSystem.*;
import com.intellij.openapi.actionSystem.ex.ActionButtonLook;
import com.intellij.openapi.actionSystem.impl.ActionButton;
import com.intellij.ui.JBColor;
import com.intellij.util.ui.JBInsets;
import com.intellij.util.ui.JBUI;
import org.jetbrains.annotations.NotNull;
import com.intellij.openapi.actionSystem.impl.IdeaActionButtonLook;


import javax.swing.*;
import java.awt.*;

public final class MyActionButton extends ActionButton {
  private static final JBColor BUTTON_SELECTED_BACKGROUND = JBColor.namedColor("SearchOption.selectedBackground", 0xDAE4ED, 0x5C6164);
  private static final ActionButtonLook FIELD_INPLACE_LOOK = new IdeaActionButtonLook() {
    @Override
    public void paintBorder(Graphics g, JComponent component, @ActionButtonComponent.ButtonState int state) {
      if (component.isFocusOwner() && component.isEnabled()) {
        Rectangle rect = new Rectangle(component.getSize());
        JBInsets.removeFrom(rect, component.getInsets());
        SYSTEM_LOOK.paintLookBorder(g, rect, JBUI.CurrentTheme.ActionButton.focusedBorder());
      } else {
        super.paintBorder(g, component, ActionButtonComponent.NORMAL);
      }
    }

    @Override
    public void paintBackground(Graphics g, JComponent component, int state) {
      if (((MyActionButton) component).isRolloverState()) {
        super.paintBackground(g, component, state);
        return;
      }
      if (state == ActionButtonComponent.SELECTED && component.isEnabled()) {
        Rectangle rect = new Rectangle(component.getSize());
        JBInsets.removeFrom(rect, component.getInsets());
        paintLookBackground(g, rect, BUTTON_SELECTED_BACKGROUND);
      }
    }
  };

  public MyActionButton(@NotNull AnAction action, boolean focusable) {
    super(action, action.getTemplatePresentation().clone(), ActionPlaces.UNKNOWN,
        ActionToolbar.DEFAULT_MINIMUM_BUTTON_SIZE);

    setLook(focusable ? FIELD_INPLACE_LOOK : ActionButtonLook.INPLACE_LOOK);
    setFocusable(focusable);
    updateIcon();
  }

  @Override
  protected DataContext getDataContext() {
    return DataManager.getInstance().getDataContext(this);
  }

  @Override
  public int getPopState() {
    return isSelected() ? SELECTED : super.getPopState();
  }

  boolean isRolloverState() {
    return super.isRollover();
  }

  @Override
  public Icon getIcon() {
    if (isEnabled() && isSelected()) {
      Icon selectedIcon = myPresentation.getSelectedIcon();
      if (selectedIcon != null)
        return selectedIcon;
    }
    return super.getIcon();
  }
}
