package com.project.gwtforum.client.widgets;

import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.PopupPanel;
import com.project.gwtforum.client.GWTForum;

public class LoadingPopup extends PopupPanel {

	public static LoadingPopup instance = new LoadingPopup();
	
	private LoadingPopup () {
		add(new Label(GWTForum.MESSAGES.loading()));
		setGlassEnabled(true);
	}
	
	public static LoadingPopup getInstance() {
		if (instance == null) {
			instance = new LoadingPopup();
		}
		return instance;
	}
}
