package com.project.gwtforum.client.widgets;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

public class CategoryWidget extends Composite {

	private static CategoriesWidgetUiBinder uiBinder = GWT
			.create(CategoriesWidgetUiBinder.class);

	interface CategoriesWidgetUiBinder extends
			UiBinder<Widget, CategoryWidget> {
	}

	public CategoryWidget() {
		initWidget(uiBinder.createAndBindUi(this));
		
	}
	
	@UiField
	Label categoryTitle;
	
	@UiField
	VerticalPanel categoryPanel;

	public Label getCategoryTitle() {
		return categoryTitle;
	}

	public void setCategoryTitle(String text) {
		this.categoryTitle.setText(text);
	}

	public VerticalPanel getCategoryPanel() {
		return categoryPanel;
	}

	public void setCategoryPanel(VerticalPanel categoryPanel) {
		this.categoryPanel = categoryPanel;
	}

}
