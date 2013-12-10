package com.project.gwtforum.client.widgets;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

public class PostsWidget extends Composite {

	private static PostsWidgetUiBinder uiBinder = GWT
			.create(PostsWidgetUiBinder.class);

	interface PostsWidgetUiBinder extends UiBinder<Widget, PostsWidget> {
	}

	public PostsWidget() {
		initWidget(uiBinder.createAndBindUi(this));
	}
	
	@UiField
	Label threadName;
	
	@UiField
	VerticalPanel postsPanel;
	
	@UiField
	TextArea newPostText;
	
	@UiField
	Button newPostButton;

	public Label getThreadName() {
		return threadName;
	}

	public VerticalPanel getPostsPanel() {
		return postsPanel;
	}

	public TextArea getNewPostText() {
		return newPostText;
	}

	public Button getNewPostButton() {
		return newPostButton;
	}
}
