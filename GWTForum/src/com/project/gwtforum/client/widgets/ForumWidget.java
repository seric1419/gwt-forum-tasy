package com.project.gwtforum.client.widgets;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

public class ForumWidget extends Composite {

	private static ForumWidgetUiBinder uiBinder = GWT
			.create(ForumWidgetUiBinder.class);

	interface ForumWidgetUiBinder extends UiBinder<Widget, ForumWidget> {
	}

	public ForumWidget() {
		initWidget(uiBinder.createAndBindUi(this));
	}

	@UiField
	Label forumTitle;
	
	@UiField
	VerticalPanel forumPanel;

	public Label getForumTitle() {
		return forumTitle;
	}

	public void setForumTitle(String text) {
		this.forumTitle.setText(text);
	}

	public VerticalPanel getForumPanel() {
		return forumPanel;
	}

	public void setForumPanel(VerticalPanel forumPanel) {
		this.forumPanel = forumPanel;
	}
	
}
