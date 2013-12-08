package com.project.gwtforum.client;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyDownEvent;
import com.google.gwt.event.dom.client.KeyDownHandler;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.project.gwtforum.client.widgets.InfoPopup;
import com.project.gwtforum.client.widgets.LoadingPopup;
import com.project.gwtforum.client.widgets.RegisterFormWidget;
import com.project.gwtforum.shared.ResponseRpc;

public class RegisterForm {

	private final RegisterFormWidget widget = new RegisterFormWidget();
	
	private boolean error;

	private AsyncCallback<ResponseRpc<Boolean>> registerCallback;
	
	public RegisterForm() {
		widget.getRegisterButton().addClickHandler(completeHandler);
		widget.getConfirmPasswordTextBox().addKeyDownHandler(onEnterHandler);
		widget.getPasswordTextBox().addKeyDownHandler(onEnterHandler);
		widget.getLoginTextBox().addKeyDownHandler(onEnterHandler);
		
		InfoPopup.getInstance().setButtonClickHandler(closeDialogHandler);
		
		initializeCallbacks();
	}
	
	public void initializeCallbacks() {
		registerCallback = new AsyncCallback<ResponseRpc<Boolean>>() {
			
			@Override
			public void onSuccess(ResponseRpc<Boolean> result) {
				error = result.isError();
				
				if (!result.isError()) {
					LoadingPopup.getInstance().hide();
					InfoPopup.getInstance().setText(GWTForum.MESSAGES.registrationSuccess());
					InfoPopup.getInstance().center();
				}
				else {
					LoadingPopup.getInstance().hide();
					String message = "";
					
					if (result.getErrorMessages().containsKey("loginValidationError")) {
						String error = result.getErrorMessages().get("loginValidationError");
						if (error.equals("tooShort")) {
							message += GWTForum.MESSAGES.tooShortLogin() + "<br/></br>";
						}
						else if (error.equals("tooLong")) {
							message += GWTForum.MESSAGES.tooLongLogin() + "<br/></br>";
						}
						else if (error.equals("excludedCharacters")) {
							message += GWTForum.MESSAGES.excludedCharacters() + "<br/></br>";
						}
						
						widget.getLoginTextBox().addStyleDependentName("error");
					}
					
					if (result.getErrorMessages().containsKey("passwordValidationError")) {
						message += GWTForum.MESSAGES.tooShortPass() + "<br/></br>";
						
						widget.getPasswordTextBox().addStyleDependentName("error");
					}
					
					if (result.getErrorMessages().containsKey("loginInUse")) {
						message += GWTForum.MESSAGES.loginInUse() + "<br/></br>";
						
						widget.getLoginTextBox().addStyleDependentName("error");
					}
					
					if (result.getErrorMessages().containsKey("unknown")) {
						message += GWTForum.MESSAGES.unknownError() + "<br/></br>";
					}
					
					InfoPopup.getInstance().setText(message);
					InfoPopup.getInstance().center();
				}
			}
			
			@Override
			public void onFailure(Throwable caught) {
				error = true;
				LoadingPopup.getInstance().hide();
				InfoPopup.getInstance().setText(GWTForum.MESSAGES.unknownError());
				InfoPopup.getInstance().center(); 
			}
		};
	}
	
	public RegisterFormWidget getWidget() {
		return widget;
	}
	
	private void sendRegisterRequest() {
		error = false;
		
		widget.getLoginTextBox().removeStyleDependentName("error");
		widget.getPasswordTextBox().removeStyleDependentName("error");
		widget.getConfirmPasswordTextBox().removeStyleDependentName("error");
		
		if (!widget.getPasswordTextBox().getText().equals(widget.getConfirmPasswordTextBox().getText())) {
			InfoPopup.getInstance().setText(GWTForum.MESSAGES.differentPass());
			widget.getPasswordTextBox().setStyleDependentName("error", true);
			widget.getConfirmPasswordTextBox().setStyleDependentName("error", true);
			error = true;
			InfoPopup.getInstance().center();
			return;
		}
		
		LoadingPopup.getInstance().center();
		
		String login = widget.getLoginTextBox().getText();
		String password = widget.getPasswordTextBox().getText();
		GWTForum.GWTFORUM_SERVICE.register(login, password, registerCallback);
	}
	
	private ClickHandler completeHandler = new ClickHandler() {
		
		@Override
		public void onClick(ClickEvent event) {
			sendRegisterRequest();
		}
	};
	
	private ClickHandler closeDialogHandler = new ClickHandler() {
		
		@Override
		public void onClick(ClickEvent event) {
			if(!error){
				History.newItem("index");
			}
			
			InfoPopup.getInstance().hide();
		}
	};
	
	private KeyDownHandler onEnterHandler = new KeyDownHandler() {
		
		@Override
		public void onKeyDown(KeyDownEvent event) {
			if (event.getNativeKeyCode() == KeyCodes.KEY_ENTER) {
				sendRegisterRequest();
			}
		}
	};
}
