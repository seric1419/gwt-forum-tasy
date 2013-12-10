package com.project.gwtforum.client.widgets;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

public class ThreadsWidget extends Composite {

	private static ThreadsWidgetUiBinder uiBinder = GWT
			.create(ThreadsWidgetUiBinder.class);

	interface ThreadsWidgetUiBinder extends UiBinder<Widget, ThreadsWidget> {
	}

	public ThreadsWidget() {
		initWidget(uiBinder.createAndBindUi(this));
	}

	@UiField
	Label forumTitle;
	
	@UiField
	VerticalPanel threadPanel;

	@UiField
	TextBox newThreadName;
	
	@UiField
	Button addThreadButton;
	
	public Label getForumTitle() {
		return forumTitle;
	}

	public void setForumTitle(String text) {
		this.forumTitle.setText(text);
	}

	public VerticalPanel getThreadPanel() {
		return threadPanel;
	}

	public void setThreadPanel(VerticalPanel threadPanel) {
		this.threadPanel = threadPanel;
	}

	public TextBox getNewThreadName() {
		return newThreadName;
	}

	public Button getAddThreadButton() {
		return addThreadButton;
	}
}
