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

public class ForumWidget extends Composite {

	private static ForumWidgetUiBinder uiBinder = GWT
			.create(ForumWidgetUiBinder.class);

	interface ForumWidgetUiBinder extends UiBinder<Widget, ForumWidget> {
	}

	public ForumWidget(int forumId) {
		initWidget(uiBinder.createAndBindUi(this));
		this.forumId = forumId;
		forumLink.addClickHandler(clickHandler);
	}

	@UiField
	Anchor forumLink;
	
	private int forumId;

	public Anchor getForumLink() {
		return forumLink;
	}
	
	public void setForumId(int forumId) {
		this.forumId = forumId;
	}
	
	public int getForumId() {
		return forumId;
	}
	
	private ClickHandler clickHandler = new ClickHandler() {
		
		@Override
		public void onClick(ClickEvent event) {
			History.newItem("forum-" + forumId);
		}
	};
}
