package com.project.gwtforum.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.RootPanel;
import com.project.gwtforum.client.internationalization.GWTForumConstants;
import com.project.gwtforum.client.internationalization.GWTForumMessages;
import com.project.gwtforum.shared.ResponseRpc;

public class GWTForum implements EntryPoint{

	public static final GWTForumServiceAsync GWTFORUM_SERVICE = GWT.create(GWTForumService.class);
	
	public static final GWTForumConstants CONSTANTS = GWT.create(GWTForumConstants.class);
	public static final GWTForumMessages MESSAGES = GWT.create(GWTForumMessages.class);
	
	private HeaderForm header = new HeaderForm();
	private RegisterForm registerForm;
	private CategoriesPage categoriesPage;
	private AdminPage adminPage;
	
	private AsyncCallback<ResponseRpc<Boolean>> adminCallback;
	
	
	@Override
	public void onModuleLoad() {
		initializeLayout();
		initializeCallbacks();
		
		History.newItem("index");
		History.addValueChangeHandler(historyHandler);
	}
	
	private void initializeLayout() {
		RootPanel.get("header").add(header.getWidget());
		Window.setTitle(CONSTANTS.windowTitle());
	}
	
	private void initializeCallbacks() {
		adminCallback = new AsyncCallback<ResponseRpc<Boolean>>() {

			@Override
			public void onFailure(Throwable caught) {
				
			}

			@Override
			public void onSuccess(ResponseRpc<Boolean> result) {
				
				if (!result.isError() && result.getResponse()) {
					if (adminPage == null) {
						adminPage = new AdminPage();
					}
					
					RootPanel.get("adminPanel").add(adminPage.getWidget());
				}
			}
		};
	}
	
	private void openRegisterForm() {
		if (registerForm == null) {
			registerForm = new RegisterForm();
		}
		
		RootPanel.get("contents").add(registerForm.getWidget());
		header.getWidget().getRegisterButton().setVisible(false);
	}
	
	private void openCategories() {
		if (categoriesPage == null) {
			categoriesPage = new CategoriesPage();
		}
		
		RootPanel.get("contents").add(categoriesPage.getWidget());
		loadAdminPanel();
	}
	
	private void loadAdminPanel() {
		GWTFORUM_SERVICE.isAdmin(adminCallback);
	}
	
	private ValueChangeHandler<String> historyHandler = new ValueChangeHandler<String>() {

		@Override
		public void onValueChange(ValueChangeEvent<String> event) {
			String token = event.getValue();
			RootPanel.get("contents").clear();
			
			if (token.equals("index")) {
				header.getWidget().getRegisterButton().setVisible(true);
			}
			else if (token.equals("register")) {
				openRegisterForm();
			}
			else if (token.equals("categories")) {
				openCategories();
			}
			else if (token.contains("forum-")) {
				// TODO parsuj numer forum i wyswietl forum o odpowiednim ID
			}
		}
	};
}
