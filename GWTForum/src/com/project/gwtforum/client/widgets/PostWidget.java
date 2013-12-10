package com.project.gwtforum.client.widgets;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;
import com.project.gwtforum.client.PostsPage;

public class PostWidget extends Composite {

	private static PostWidgetUiBinder uiBinder = GWT
			.create(PostWidgetUiBinder.class);

	interface PostWidgetUiBinder extends UiBinder<Widget, PostWidget> {
	}

	public PostWidget(int postId) {
		initWidget(uiBinder.createAndBindUi(this));
		deleteButton.setVisible(false);
		this.postId = postId;
		deleteButton.addClickHandler(deleteButtonClickHandler);
	}

	private int postId;

	@UiField
	Label usernameLabel;

	@UiField
	Label numberOfPostsLabel;

	@UiField
	Label numberOfPostsValue;

	@UiField
	Label dataLabel;

	@UiField
	Label messageLabel;

	@UiField
	Button deleteButton;

	public Label getUsernameLabel() {
		return usernameLabel;
	}

	public Label getNumberOfPostsLabel() {
		return numberOfPostsLabel;
	}

	public Label getNumberOfPostsValue() {
		return numberOfPostsValue;
	}

	public Label getDataLabel() {
		return dataLabel;
	}

	public Label getMessageLabel() {
		return messageLabel;
	}

	public Button getDeleteButton() {
		return deleteButton;
	}

	public void setVisible(boolean visible) {
		usernameLabel.setVisible(visible);
		numberOfPostsLabel.setVisible(visible);
		numberOfPostsValue.setVisible(visible);
		dataLabel.setVisible(visible);
		messageLabel.setVisible(visible);
		deleteButton.setVisible(visible);
	}

	private ClickHandler deleteButtonClickHandler = new ClickHandler() {

		@Override
		public void onClick(ClickEvent event) {
			PostsPage.deletePost(postId);
		}
	};

}