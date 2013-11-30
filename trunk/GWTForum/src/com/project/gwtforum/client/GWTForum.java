package com.project.gwtforum.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.RootPanel;
import com.project.gwtforum.client.internationalization.GWTForumConstants;
import com.project.gwtforum.client.widgets.Header;

public class GWTForum implements EntryPoint{

	public static GWTForumConstants constants = GWT.create(GWTForumConstants.class);
	private Header headerWidget = new Header();
	
	@Override
	public void onModuleLoad() {
		initializeLayout();
		initializeClickHandlers();
		
		History.newItem(headerWidget.getTitle());
	}
	
	private void initializeLayout() {
		RootPanel.get("header").add(headerWidget);
		Window.setTitle(constants.windowTitle());
	}

	private void initializeClickHandlers() {
		headerWidget.getRegisterButton().addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				RegisterForm form = new RegisterForm();
				RootPanel.get("contents").add(form.getWidget());
			}
		});
	}
}
