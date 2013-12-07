package com.project.gwtforum.client.widgets;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;

public class CategoriesWidget extends Composite {

	private static CategoriesWidgetUiBinder uiBinder = GWT
			.create(CategoriesWidgetUiBinder.class);

	interface CategoriesWidgetUiBinder extends
			UiBinder<Widget, CategoriesWidget> {
	}

	public CategoriesWidget() {
		initWidget(uiBinder.createAndBindUi(this));
		
		categoryTitle.setText("Kategorie:");
	}

	@UiField
	Label categoryTitle;

}
