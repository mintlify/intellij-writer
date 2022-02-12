package com.mintlify.document.ui;

import com.intellij.openapi.project.Project;
import com.intellij.openapi.util.Key;
import com.intellij.openapi.wm.ToolWindow;
import com.intellij.openapi.wm.ToolWindowFactory;
import com.intellij.openapi.wm.ToolWindowManager;
import com.intellij.openapi.wm.ex.ToolWindowManagerEx;
import com.intellij.ui.content.Content;
import com.intellij.ui.content.ContentFactory;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class MyToolWindowFactory implements ToolWindowFactory {
  public static final String ID = "AI Doc Writer";
  private static final Key<MyToolWindow> MY_TOOL_WINDOW = Key.create("MyToolWindow");

  /**
   * Create the tool window content.
   *
   * @param project    current project
   * @param toolWindow current tool window
   */
  public void createToolWindowContent(@NotNull Project project, @NotNull ToolWindow toolWindow) {
    MyToolWindow myToolWindow = new MyToolWindow(toolWindow);
    ContentFactory contentFactory = ContentFactory.SERVICE.getInstance();
    Content content = contentFactory.createContent(myToolWindow.getContent(), null, false);
    content.putUserData(MY_TOOL_WINDOW, myToolWindow);
    toolWindow.getContentManager().addContent(content);
  }

  @Nullable
  public static MyToolWindow getMyToolWindow(Project project) {
    ToolWindowManager instance = ToolWindowManagerEx.getInstance(project);
    ToolWindow toolWindow = instance.getToolWindow(MyToolWindowFactory.ID);
    if (toolWindow != null) {
      if (!toolWindow.isShowStripeButton()) {
        toolWindow.show();
      }
      Content[] contents = toolWindow.getContentManager().getContents();
      if (contents.length > 0) {
        Content content = contents[0];
        return content.getUserData(MY_TOOL_WINDOW);
      }
    }
    return null;
  }

}