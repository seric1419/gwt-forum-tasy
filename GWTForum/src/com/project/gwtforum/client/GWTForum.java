package com.project.gwtforum.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.RootPanel;
import com.project.gwtforum.client.widgets.Header;

public class GWTForum implements EntryPoint{

	private GWTForumConstants constants = GWT.create(GWTForumConstants.class);
	private Header headerWidget = new Header();
	
	@Override
	public void onModuleLoad() {
		initializeLayout();
	}
	
	private void initializeLayout() {
		RootPanel.get("header").add(headerWidget);
		Window.setTitle(constants.windowTitle());
		
		headerWidget.getForumTitle().setText(constants.windowTitle());
		headerWidget.getForumTitle().setStyleName("h1");
	}

}
