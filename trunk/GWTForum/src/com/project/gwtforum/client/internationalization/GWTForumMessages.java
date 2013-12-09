package com.project.gwtforum.client.internationalization;

import com.google.gwt.i18n.client.Messages;

public interface GWTForumMessages extends Messages{
	@DefaultMessage("Ładowanie, proszę czekać..")
	String loading();
	
	@DefaultMessage("Rejestracja przebiegła poprawnie.")
	String registrationSuccess();
	
	/*
	 * Errors
	 */
	
	@DefaultMessage("Podane hasło lub login są nieprawidłowe.")
	String loginError();
	
	@DefaultMessage("Wystąpił nieznany błąd.")
	String unknownError();
	
	@DefaultMessage("Login jest zbyt krótki. Musi się on składać z przynajmniej 3 znaków.")
	String tooShortLogin();
	
	@DefaultMessage("Login jest zbyt długi. Musi się on składać z conajwyżej 3 znaków.")
	String tooLongLogin();
	
	@DefaultMessage("Użyłeś niedozwolonych znaków w loginie. Może się on składać z liter A-Z, cyfr 0-9 oraz \"_\" \".\" \"-\"")
	String excludedCharacters();
	
	@DefaultMessage("Hasło jest zbyt krótkie. Powinno ono składać się z przynajmniej 8 znaków.")
	String tooShortPass();
	
	@DefaultMessage("Podany login jest już zajęty.")
	String loginInUse();
	
	@DefaultMessage("Podane hasła nie są takie same")
	String differentPass();
	
	@DefaultMessage("Brak uprawnień lub nie jesteś zalogowany/a.")
	String userPermissionError();
}
