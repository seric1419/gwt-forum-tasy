package com.project.gwtforum.client.widgets;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

public class ThreadWidget extends Composite {

	private static ThreadWidgetUiBinder uiBinder = GWT
			.create(ThreadWidgetUiBinder.class);

	interface ThreadWidgetUiBinder extends UiBinder<Widget, ThreadWidget> {
	}

	public ThreadWidget() {
		initWidget(uiBinder.createAndBindUi(this));
	}

	@UiField
	Label threadTitle;
	
	@UiField
	VerticalPanel threadPanel;

	public Label getThreadTitle() {
		return threadTitle;
	}

	public void setThreadTitle(String text) {
		this.threadTitle.setText(text);
	}

	public VerticalPanel getThreadPanel() {
		return threadPanel;
	}

	public void setThreadPanel(VerticalPanel threadPanel) {
		this.threadPanel = threadPanel;
	}
	
}
