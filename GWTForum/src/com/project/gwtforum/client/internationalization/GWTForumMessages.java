package com.project.gwtforum.client.internationalization;

import com.google.gwt.i18n.client.Messages;

public interface GWTForumMessages extends Messages{
	@DefaultMessage("Ładowanie, proszę czekać..")
	String loading();
	
	@DefaultMessage("Rejestracja przebiegła poprawnie.")
	String registrationSuccess();
}
