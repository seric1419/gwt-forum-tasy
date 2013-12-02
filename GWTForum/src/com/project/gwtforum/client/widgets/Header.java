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

public class Header extends Composite{

	private static HeaderUiBinder uiBinder = GWT.create(HeaderUiBinder.class);

	interface HeaderUiBinder extends UiBinder<Widget, Header> {
	}

	public Header() {
		initWidget(uiBinder.createAndBindUi(this));
		super.setSize("100em", "10em");
		
		forumTitle.setText(GWTForum.constants.forumTitle());
		forumTitle.setStyleName("header");
		loginLabel.setText(GWTForum.constants.login());
		loginTextBox.addStyleDependentName("login");
		passwordLabel.setText(GWTForum.constants.password());
		passwordTextBox.addStyleDependentName("login");
		loginButton.setText(GWTForum.constants.login());
		loginButton.addStyleDependentName("right");
		registerButton.setText(GWTForum.constants.register());
		registerButton.addStyleDependentName("right");
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
	}
}