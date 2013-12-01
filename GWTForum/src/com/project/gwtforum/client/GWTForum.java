package com.project.gwtforum.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.RootPanel;
import com.project.gwtforum.client.internationalization.GWTForumConstants;
import com.project.gwtforum.client.internationalization.GWTForumMessages;
import com.project.gwtforum.client.widgets.Header;

public class GWTForum implements EntryPoint{

	public static final GWTForumServiceAsync gwtForumService = GWT.create(GWTForumService.class);
	
	public static final GWTForumConstants constants = GWT.create(GWTForumConstants.class);
	public static final GWTForumMessages messages = GWT.create(GWTForumMessages.class);
	
	private Header headerWidget = new Header();
	private RegisterForm registerForm;
	
	
	@Override
	public void onModuleLoad() {
		initializeLayout();
		initializeClickHandlers();
		
		History.newItem("index");
		History.addValueChangeHandler(historyHandler);
	}
	
	private void initializeLayout() {
		RootPanel.get("header").add(headerWidget);
		Window.setTitle(constants.windowTitle());
	}

	private void initializeClickHandlers() {
		headerWidget.getRegisterButton().addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				History.newItem("register");
			}
		});
	}
	
	private void openRegisterForm() {
		if (registerForm == null){
			registerForm = new RegisterForm();
		}
		
		RootPanel.get("contents").add(registerForm.getWidget());
		headerWidget.getRegisterButton().setVisible(false);
	}
	
	private ValueChangeHandler<String> historyHandler = new ValueChangeHandler<String>() {

		@Override
		public void onValueChange(ValueChangeEvent<String> event) {
			String token = event.getValue();
			RootPanel.get("contents").clear();
			
			if (token.equals("index")) {
				headerWidget.getRegisterButton().setVisible(true);
			}
			else if (token.equals("register")) {
				openRegisterForm();
			}
		}
	};
}
