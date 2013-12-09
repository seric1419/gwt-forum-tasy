package com.project.gwtforum.client.widgets;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;

public class AdminPanelWidget extends Composite {

	private static AdminPanelWidgetUiBinder uiBinder = GWT
			.create(AdminPanelWidgetUiBinder.class);

	interface AdminPanelWidgetUiBinder extends
			UiBinder<Widget, AdminPanelWidget> {
	}

	public AdminPanelWidget() {
		initWidget(uiBinder.createAndBindUi(this));
	}

	@UiField
	Label categoryLabel;
	
	@UiField
	Label forumLabel;
	
	@UiField
	Label nameLabel;
	
	@UiField
	TextBox categoryNameTextBox;
	
	@UiField
	TextBox forumNameTextBox;
	
	@UiField
	Label sectionLabel;
	
	@UiField
	ListBox forumListBox;
	
	@UiField
	Button categoryButton;
	
	@UiField
	Button forumButton;

	public Label getCategoryLabel() {
		return categoryLabel;
	}

	public Label getForumLabel() {
		return forumLabel;
	}

	public Label getNameLabel() {
		return nameLabel;
	}

	public TextBox getCategoryNameTextBox() {
		return categoryNameTextBox;
	}

	public TextBox getForumNameTextBox() {
		return forumNameTextBox;
	}

	public Label getSectionLabel() {
		return sectionLabel;
	}

	public ListBox getForumListBox() {
		return forumListBox;
	}

	public Button getCategoryButton() {
		return categoryButton;
	}

	public Button getForumButton() {
		return forumButton;
	}
}
