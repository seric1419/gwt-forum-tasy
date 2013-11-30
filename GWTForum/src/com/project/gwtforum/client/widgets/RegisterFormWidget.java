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
import com.project.gwtforum.client.internationalization.GWTForumConstants;

public class RegisterFormWidget extends Composite{

	private static RegisterFormWidgetUiBinder uiBinder = GWT
			.create(RegisterFormWidgetUiBinder.class);

	interface RegisterFormWidgetUiBinder extends UiBinder<Widget, RegisterFormWidget> {
	}

	public RegisterFormWidget() {
		initWidget(uiBinder.createAndBindUi(this));
		super.setSize("100em", "30em");
		
		loginLabel.setText(GWTForum.constants.login());
		passwordLabel.setText(GWTForum.constants.password());
		confirmPasswordLabel.setText(GWTForum.constants.confirmPassword());
	}
	
	@UiField
	Label loginLabel;
	
	@UiField
	TextBox loginTextBox;
	
	@UiField
	Label passwordLabel;
	
	@UiField
	PasswordTextBox passwordTextBox;
	
	@UiField
	Label confirmPasswordLabel;
	
	@UiField
	PasswordTextBox confirmPasswordTextBox;
	
	@UiField
	Button registerButton;

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

	public Label getConfirmPasswordLabel() {
		return confirmPasswordLabel;
	}

	public PasswordTextBox getConfirmPasswordTextBox() {
		return confirmPasswordTextBox;
	}

	public Button getRegisterButton() {
		return registerButton;
	}
}
