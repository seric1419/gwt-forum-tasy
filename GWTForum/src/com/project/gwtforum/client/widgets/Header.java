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
import com.project.gwtforum.client.GWTForumConstants;

public class Header extends Composite{

	private GWTForumConstants constants = GWT.create(GWTForumConstants.class);
	private static HeaderUiBinder uiBinder = GWT.create(HeaderUiBinder.class);

	interface HeaderUiBinder extends UiBinder<Widget, Header> {
	}

	public Header() {
		initWidget(uiBinder.createAndBindUi(this));
		super.setSize("100em", "10em");
		
		forumTitle.setText(constants.forumTitle());
		forumTitle.setStyleName("header");
		loginLabel.setText(constants.login());
		loginTextBox.addStyleDependentName("login");
		passwordLabel.setText(constants.password());
		passwordTextBox.addStyleDependentName("login");
		loginButton.setText(constants.login());
		loginButton.addStyleDependentName("right");
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
}
