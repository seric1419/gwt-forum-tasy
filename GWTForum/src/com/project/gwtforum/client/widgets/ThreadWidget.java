package com.project.gwtforum.client.widgets;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;

public class ThreadWidget extends Composite {

	private static ThreadWidgetUiBinder uiBinder = GWT
			.create(ThreadWidgetUiBinder.class);

	interface ThreadWidgetUiBinder extends UiBinder<Widget, ThreadWidget> {
	}

	public ThreadWidget(int threadId, int forumId) {
		initWidget(uiBinder.createAndBindUi(this));
		this.threadId = threadId;
		this.forumId = forumId;
		threadLink.addClickHandler(clickHandler);
	}

	private int threadId;
	private int forumId;
	
	@UiField
	Anchor threadLink;

	public int getThreadId() {
		return threadId;
	}

	public void setThreadId(int threadId) {
		this.threadId = threadId;
	}

	public int getForumId() {
		return forumId;
	}

	public void setForumId(int forumId) {
		this.forumId = forumId;
	}

	public Anchor getThreadLink() {
		return threadLink;
	}
	
	private ClickHandler clickHandler = new ClickHandler() {
		
		@Override
		public void onClick(ClickEvent event) {
			History.newItem("forum-" + forumId + "/thread-" + threadId + "-" + threadLink.getText());
		}
	};
}
