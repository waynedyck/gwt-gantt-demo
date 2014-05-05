package com.imaginedreal.gwtganttdemo.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.ui.RootLayoutPanel;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class GWTGanttDemoEntryPoint implements EntryPoint {
  
  @Override
  public void onModuleLoad() {
    RootLayoutPanel.get().add(new DemoPanel());
  }
  
}
