package com.project.gwtforum.client.internationalization;

import com.google.gwt.i18n.client.Constants;

public interface GWTForumConstants extends Constants{

	@DefaultStringValue("GWTForum")
	String windowTitle();
	
	@DefaultStringValue("Login")
	String login();
	
	@DefaultStringValue("Hasło")
	String password();
	
	@DefaultStringValue("GWT Forum")
	String forumTitle();
	
	@DefaultStringValue("Rejestruj")
	String register();
}
