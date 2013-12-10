package com.project.gwtforum.client;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.project.gwtforum.client.widgets.InfoPopup;
import com.project.gwtforum.client.widgets.LoadingPopup;
import com.project.gwtforum.client.widgets.ThreadWidget;
import com.project.gwtforum.client.widgets.ThreadsWidget;
import com.project.gwtforum.shared.ResponseRpc;
import com.project.gwtforum.shared.ThreadRpc;

public class ThreadsPage {

	private final ThreadsWidget widget = new ThreadsWidget();
	
	private AsyncCallback<ResponseRpc<ThreadRpc>> threadsCallback;
	
	private int forumId;
	
	public ThreadsPage(int forumId, String forumName) {
		this.forumId = forumId;
		initializeCallbacks();
		
		widget.getForumTitle().setText(forumName);
		widget.getForumTitle().addStyleName("categoryHeader");
		widget.getNewThreadName().addStyleDependentName("register");
		widget.getAddThreadButton().addClickHandler(addThreadHandler);
		widget.getAddThreadButton().setText(GWTForum.CONSTANTS.addNew());
		widget.getAddThreadButton().addStyleDependentName("right");
		
		LoadingPopup.getInstance().show();
		InfoPopup.getInstance().setButtonClickHandler(closeDialogHandler);
		GWTForum.GWTFORUM_SERVICE.getThreads(forumId, threadsCallback);
	}
	
	private void initializeCallbacks() {
		threadsCallback = new AsyncCallback<ResponseRpc<ThreadRpc>>() {

			@Override
			public void onFailure(Throwable caught) {
				LoadingPopup.getInstance().hide();
				InfoPopup.getInstance().setText(GWTForum.MESSAGES.unknownError());
				InfoPopup.getInstance().center();
			}

			@Override
			public void onSuccess(ResponseRpc<ThreadRpc> result) {
				
				if (!result.isError()) {
					
					if (result.getResponseCollection().size() > 0) {
						widget.getThreadPanel().clear();
						
						for (ThreadRpc temp: result.getResponseCollection()) {
							ThreadWidget threadWidget = new ThreadWidget(temp.getId(), forumId);
							threadWidget.getThreadLink().setText(temp.getName());
							threadWidget.addStyleName("bordered");
							
							widget.getThreadPanel().add(threadWidget);
						}
					}
					
					LoadingPopup.getInstance().hide();
				}
				else {
					LoadingPopup.getInstance().hide();
					
					if (result.getErrorMessages().containsKey("notLoggedIn")) {
						InfoPopup.getInstance().setText(GWTForum.MESSAGES.userPermissionError());
						InfoPopup.getInstance().center();
						History.newItem("index");
					}
					else {
						InfoPopup.getInstance().setText(GWTForum.MESSAGES.unknownError());
						InfoPopup.getInstance().center();
					}
				}
			}
		};
	}
	
	public ThreadsWidget getWidget() {
		return widget;
	}
	
	private ClickHandler closeDialogHandler = new ClickHandler() {
		
		@Override
		public void onClick(ClickEvent event) {
			InfoPopup.getInstance().hide();
		}
	};
	
	private ClickHandler addThreadHandler = new ClickHandler() {
		
		@Override
		public void onClick(ClickEvent event) {
			LoadingPopup.getInstance().show();
			ThreadRpc newThread = new ThreadRpc();
			
			newThread.setName(widget.getNewThreadName().getText());
			newThread.setForumId(forumId);
			
			widget.getNewThreadName().setText("");
			
			GWTForum.GWTFORUM_SERVICE.saveThread(newThread, threadsCallback);
		}
	};
}
