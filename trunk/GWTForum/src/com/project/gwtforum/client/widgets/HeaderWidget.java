package com.project.gwtforum.client.widgets;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.PasswordTextBox;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;
import com.project.gwtforum.client.GWTForum;

public class HeaderWidget extends Composite{

	private static HeaderWidgetUiBinder uiBinder = GWT.create(HeaderWidgetUiBinder.class);

	interface HeaderWidgetUiBinder extends UiBinder<Widget, HeaderWidget> {
	}

	public HeaderWidget() {
		initWidget(uiBinder.createAndBindUi(this));
		super.setSize("100em", "10em");
		
		forumTitle.setText(GWTForum.CONSTANTS.forumTitle());
		forumTitle.setStyleName("header");
		loginLabel.setText(GWTForum.CONSTANTS.login());
		loginTextBox.addStyleDependentName("login");
		passwordLabel.setText(GWTForum.CONSTANTS.password());
		passwordTextBox.addStyleDependentName("login");
		loginButton.setText(GWTForum.CONSTANTS.login());
		loginButton.addStyleDependentName("right");
		registerButton.setText(GWTForum.CONSTANTS.register());
		registerButton.addStyleDependentName("right");
		logoutButton.setVisible(false);
		logoutButton.setText(GWTForum.CONSTANTS.logout());
	}

	@UiField
	Label forumTitle;
	
	@UiField
	Label loginLabel;
	
	@UiField
	TextBox loginTextBox;
	
	@UiField
	Label passwordLabel;
	
	@UiField
	PasswordTextBox passwordTextBox;
	
	@UiField
	Button logoutButton;
	
	@UiField
	Button loginButton;
	
	@UiField
	Button registerButton;

	public Label getForumTitle() {
		return forumTitle;
	}

	public Label getLoginLabel() {
		return loginLabel;
	}

	public TextBox getLoginTextBox() {
		return loginTextBox;
	}

	public Label getPasswordLabel() {
		return passwordLabel;
	}

	public PasswordTextBox getPasswordTextBox() {
		return passwordTextBox;
	}
	
	public Button getLogoutButton() {
		return logoutButton;
	}

	public Button getLoginButton() {
		return loginButton;
	}
	
	public Button getRegisterButton() {
		return registerButton;
	}
	
	public void setVisible(boolean visible) {
		loginLabel.setVisible(visible);
		loginTextBox.setVisible(visible);
		passwordLabel.setVisible(visible);
		passwordTextBox.setVisible(visible);
		loginButton.setVisible(visible);
		registerButton.setVisible(visible);
		logoutButton.setVisible(!visible);
	}
}
