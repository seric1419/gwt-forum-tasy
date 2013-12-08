package com.project.gwtforum.client;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyDownEvent;
import com.google.gwt.event.dom.client.KeyDownHandler;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.project.gwtforum.client.widgets.HeaderWidget;
import com.project.gwtforum.client.widgets.InfoPopup;
import com.project.gwtforum.client.widgets.LoadingPopup;
import com.project.gwtforum.shared.ResponseRpc;

public class HeaderForm {

	private final HeaderWidget widget = new HeaderWidget();
	
	private AsyncCallback<ResponseRpc<Boolean>> loginCallback;
	private AsyncCallback<ResponseRpc<Boolean>> logoutCallback;
	
	public HeaderForm() {
		widget.getRegisterButton().addClickHandler(registerClickHandler);
		widget.getLoginButton().addClickHandler(loginClickHandler);
		widget.getLogoutButton().addClickHandler(logoutClickHandler);
		widget.getPasswordTextBox().addKeyDownHandler(onEnterHandler);
		
		InfoPopup.getInstance().setButtonClickHandler(closeDialogClickHandler);
		
		initializeCallbacks();
	}
	
	private void initializeCallbacks() {
		loginCallback = new AsyncCallback<ResponseRpc<Boolean>>() {

			@Override
			public void onFailure(Throwable caught) {
				LoadingPopup.getInstance().hide();
				InfoPopup.getInstance().setText(GWTForum.MESSAGES.unknownError());
				InfoPopup.getInstance().center(); 
			}

			@Override
			public void onSuccess(ResponseRpc<Boolean> result) {
				
				if (!result.isError()) {
					LoadingPopup.getInstance().hide();
					InfoPopup.getInstance().setText("Sukces!");
					InfoPopup.getInstance().center();
					widget.setVisible(false);
				}
				else {
					String message = "";
					
					if (result.getErrorMessages().containsKey("wrongPassword") || result.getErrorMessages().containsKey("wrongLogin")) {
						message += GWTForum.MESSAGES.loginError() + "<br/><br/>";
						
						widget.getLoginTextBox().addStyleDependentName("error");
						widget.getPasswordTextBox().addStyleDependentName("error");
					}
					if (result.getErrorMessages().containsKey("unknown")) {
						message += GWTForum.MESSAGES.unknownError() + "<br/><br/>";
					}

					LoadingPopup.getInstance().hide();
					InfoPopup.getInstance().setText(message);
					InfoPopup.getInstance().center();
				}
				
			}
		};
		
		logoutCallback = new AsyncCallback<ResponseRpc<Boolean>>() {

			@Override
			public void onFailure(Throwable caught) {
				LoadingPopup.getInstance().hide();
				InfoPopup.getInstance().setText(GWTForum.MESSAGES.unknownError());
				InfoPopup.getInstance().center(); 
			}

			@Override
			public void onSuccess(ResponseRpc<Boolean> result) {
				LoadingPopup.getInstance().hide();
				
				if (!result.isError()) {
					InfoPopup.getInstance().setText("Wylogowano!");
					InfoPopup.getInstance().center();
					widget.setVisible(true);
				}
				else {
					InfoPopup.getInstance().setText(GWTForum.MESSAGES.unknownError());
					InfoPopup.getInstance().center();
				}
				
			}
		};
	}
	
	public HeaderWidget getWidget() {
		return widget;
	}
	
	private void sendLoginRequest() {
		LoadingPopup.getInstance().center();
		widget.getLoginTextBox().removeStyleDependentName("error");
		widget.getPasswordTextBox().removeStyleDependentName("error");
		GWTForum.GWTFORUM_SERVICE.login(widget.getLoginTextBox().getText(),
				widget.getPasswordTextBox().getText(), loginCallback);
	}
	
	private ClickHandler registerClickHandler = new ClickHandler() {
		
		@Override
		public void onClick(ClickEvent event) {
			History.newItem("register");
		}
	};
	
	private ClickHandler loginClickHandler = new ClickHandler() {
		
		@Override
		public void onClick(ClickEvent event) {
			sendLoginRequest();
		}
	};
	
	private ClickHandler logoutClickHandler = new ClickHandler() {
		
		@Override
		public void onClick(ClickEvent event) {
			LoadingPopup.getInstance().center();
			GWTForum.GWTFORUM_SERVICE.logout(logoutCallback);
		}
	};
	
	private ClickHandler closeDialogClickHandler = new ClickHandler() {
		
		@Override
		public void onClick(ClickEvent event) {
			InfoPopup.getInstance().hide();
		}
	};
	
	private KeyDownHandler onEnterHandler = new KeyDownHandler() {
		
		@Override
		public void onKeyDown(KeyDownEvent event) {
			if (event.getNativeKeyCode() == KeyCodes.KEY_ENTER) {
				sendLoginRequest();
			}
		}
	};
}
