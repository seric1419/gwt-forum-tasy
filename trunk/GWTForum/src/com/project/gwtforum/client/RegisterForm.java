package com.project.gwtforum.client;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyDownEvent;
import com.google.gwt.event.dom.client.KeyDownHandler;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.project.gwtforum.client.widgets.RegisterFormWidget;
import com.project.gwtforum.shared.ResponseRpc;

public class RegisterForm {

	private final RegisterFormWidget widget = new RegisterFormWidget();
	private final PopupPanel loadingPopup = new PopupPanel(false, false);
	private final PopupPanel responsePopup = new PopupPanel(false, false);
	
	private boolean error;
	private HTML messageLabel;

	private AsyncCallback<ResponseRpc<Boolean>> registerCallback;
	
	public RegisterForm() {
		widget.getRegisterButton().addClickHandler(completeHandler);
		widget.getConfirmPasswordTextBox().addKeyDownHandler(onEnterHandler);
		widget.getPasswordTextBox().addKeyDownHandler(onEnterHandler);
		widget.getLoginTextBox().addKeyDownHandler(onEnterHandler);
		
		loadingPopup.add(new Label(GWTForum.messages.loading()));
		loadingPopup.setGlassEnabled(true);
		responsePopup.setAnimationEnabled(true);
		responsePopup.setGlassEnabled(true);
		
		VerticalPanel vPanel = new VerticalPanel();
		Button closeButton = new Button(GWTForum.constants.close());
		closeButton.addClickHandler(closeDialogHandler);
		closeButton.addStyleDependentName("right");
		messageLabel = new HTML();
		messageLabel.setStyleName("boldText");
		vPanel.add(messageLabel);
		vPanel.add(closeButton);
		
		responsePopup.setWidget(vPanel);
		
		initializeCallbacks();
	}
	
	public void initializeCallbacks() {
		registerCallback = new AsyncCallback<ResponseRpc<Boolean>>() {
			
			@Override
			public void onSuccess(ResponseRpc<Boolean> result) {
				error = result.isError();
				
				if (!result.isError()) {
					loadingPopup.hide();
					messageLabel.setHTML(GWTForum.messages.registrationSuccess());
					responsePopup.center();
				}
				else {
					error = true;
					loadingPopup.hide();
					String message = "";
					
					if (result.getErrorMessages().containsKey("loginValidationError")) {
						String error = result.getErrorMessages().get("loginValidationError");
						if (error.equals("tooShort")) {
							message += GWTForum.messages.tooShortLogin() + "<br/></br>";
						}
						else if (error.equals("tooLong")) {
							message += GWTForum.messages.tooLongLogin() + "<br/></br>";
						}
						else if (error.equals("excludedCharacters")) {
							message += GWTForum.messages.excludedCharacters() + "<br/></br>";
						}
						
						widget.getLoginTextBox().addStyleDependentName("error");
					}
					
					if (result.getErrorMessages().containsKey("passwordValidationError")) {
						message += GWTForum.messages.tooShortPass() + "<br/></br>";
						
						widget.getPasswordTextBox().addStyleDependentName("error");
					}
					
					if (result.getErrorMessages().containsKey("loginInUse")) {
						message += GWTForum.messages.loginInUse() + "<br/></br>";
						
						widget.getLoginTextBox().addStyleDependentName("error");
					}
					
					if (result.getErrorMessages().containsKey("unknown")) {
						message += GWTForum.messages.unknownError() + "<br/></br>";
					}
					
					messageLabel.setHTML(message);
					responsePopup.center();
				}
			}
			
			@Override
			public void onFailure(Throwable caught) {
				error = true;
				loadingPopup.hide();
				messageLabel.setHTML(GWTForum.messages.unknownError());
				responsePopup.center(); 
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
			messageLabel.setHTML(GWTForum.messages.differentPass());
			widget.getPasswordTextBox().setStyleDependentName("error", true);
			widget.getConfirmPasswordTextBox().setStyleDependentName("error", true);
			error = true;
			responsePopup.center();
			return;
		}
		
		loadingPopup.center();
		
		String login = widget.getLoginTextBox().getText();
		String password = widget.getPasswordTextBox().getText();
		GWTForum.gwtForumService.register(login, password, registerCallback);
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
			
			responsePopup.hide();
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
