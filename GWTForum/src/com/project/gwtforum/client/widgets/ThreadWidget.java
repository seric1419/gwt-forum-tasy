package com.project.gwtforum.client.widgets;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;

public class ThreadWidget extends Composite {

	private static ThreadWidgetUiBinder uiBinder = GWT
			.create(ThreadWidgetUiBinder.class);

	interface ThreadWidgetUiBinder extends UiBinder<Widget, ThreadWidget> {
	}

	public ThreadWidget() {
		initWidget(uiBinder.createAndBindUi(this));
	}

}
