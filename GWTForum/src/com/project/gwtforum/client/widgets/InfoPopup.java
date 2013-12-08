package com.project.gwtforum.client.widgets;

import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.web.bindery.event.shared.HandlerRegistration;
import com.project.gwtforum.client.GWTForum;

public class InfoPopup extends PopupPanel{

	private static InfoPopup instance = new InfoPopup();
	
	private HandlerRegistration handlerRegistration;
	
	private Button closeButton;
	private HTML messageLabel;
	
	private InfoPopup() {
		setAnimationEnabled(true);
		setGlassEnabled(true);
		
		VerticalPanel vPanel = new VerticalPanel();
		closeButton = new Button(GWTForum.CONSTANTS.close());
		closeButton.addStyleDependentName("right");
		messageLabel = new HTML();
		messageLabel.setStyleName("boldText");
		vPanel.add(messageLabel);
		vPanel.add(closeButton);
		
		setWidget(vPanel);
	}
	
	public static InfoPopup getInstance() {
		if (instance == null) {
			instance = new InfoPopup();
		}
		return instance;
	}
	
	public void setButtonClickHandler(ClickHandler clickHandler) {
		if (handlerRegistration != null) {
			handlerRegistration.removeHandler();
		}
		handlerRegistration = closeButton.addClickHandler(clickHandler);
	}
	
	public void setText(String messageText) {
		messageLabel.setHTML(messageText);
	}
}
