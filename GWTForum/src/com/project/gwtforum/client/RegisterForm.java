package com.project.gwtforum.client;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
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
	private Label messageLabel;

	private AsyncCallback<ResponseRpc<Boolean>> registerCallback;
	
	public RegisterForm() {
		widget.getRegisterButton().addClickHandler(completeHandler);
		
		loadingPopup.add(new Label(GWTForum.messages.loading()));
		loadingPopup.setGlassEnabled(true);
		responsePopup.setAnimationEnabled(true);
		responsePopup.setGlassEnabled(true);
		VerticalPanel vPanel = new VerticalPanel();
		Button closeButton = new Button(GWTForum.constants.close());
		closeButton.addClickHandler(closeDialogHandler);
		closeButton.addStyleDependentName("right");
		messageLabel = new Label();
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
					messageLabel.setText(GWTForum.messages.registrationSuccess());
					responsePopup.center();
				}
				else {
					
				}
			}
			
			@Override
			public void onFailure(Throwable caught) {
				
			}
		};
	}
	
	public RegisterFormWidget getWidget() {
		return widget;
	}
	
	private ClickHandler completeHandler = new ClickHandler() {
		
		@Override
		public void onClick(ClickEvent event) {
			loadingPopup.center();
			
			String login = widget.getLoginTextBox().getText();
			String password = widget.getPasswordTextBox().getText();
			GWTForum.gwtForumService.register(login, password, registerCallback);
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
}
