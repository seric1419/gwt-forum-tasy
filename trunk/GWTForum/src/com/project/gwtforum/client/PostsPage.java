package com.project.gwtforum.client;

import java.util.ArrayList;
import java.util.HashMap;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.project.gwtforum.client.widgets.InfoPopup;
import com.project.gwtforum.client.widgets.LoadingPopup;
import com.project.gwtforum.client.widgets.PostWidget;
import com.project.gwtforum.client.widgets.PostsWidget;
import com.project.gwtforum.shared.ReplyRpc;
import com.project.gwtforum.shared.ResponseRpc;
import com.project.gwtforum.shared.UserRpc;

public class PostsPage {

	private final PostsWidget widget = new PostsWidget();
	
	private static AsyncCallback<ResponseRpc<ReplyRpc>> repliesCallback;
	private AsyncCallback<ResponseRpc<Boolean>> adminCallback;
	private AsyncCallback<ResponseRpc<UserRpc>> usersCallback;
	
	private HashMap<Integer, ArrayList<PostWidget>> authorsWidgets;
	
	private int threadId;
	private boolean isAdmin;
	private final String delete = GWTForum.CONSTANTS.delete();
	
	public PostsPage(int threadId, String forumName) {
		this.threadId = threadId;
		authorsWidgets = new HashMap<Integer, ArrayList<PostWidget>>();
		
		widget.getThreadName().setText(forumName);
		widget.getNewPostButton().setText(GWTForum.CONSTANTS.addNew());
		widget.getNewPostButton().addClickHandler(newPostClickHandler);
		widget.getNewPostButton().addStyleDependentName("right");
		
		initializeCallbacks();
		
		LoadingPopup.getInstance().show();
		InfoPopup.getInstance().setButtonClickHandler(closeDialogClickHandler);
		GWTForum.GWTFORUM_SERVICE.isAdmin(adminCallback);
	}
	
	private void initializeCallbacks() {
		repliesCallback = new AsyncCallback<ResponseRpc<ReplyRpc>>() {
			
			@Override
			public void onSuccess(ResponseRpc<ReplyRpc> result) {
				
				if (!result.isError()) {
					widget.getPostsPanel().clear();
					
					if (result.getResponseCollection().size() > 0) {
						
						for (ReplyRpc temp: result.getResponseCollection()) {
							PostWidget postWidget = new PostWidget(temp.getId());
							postWidget.getMessageLabel().setText(temp.getMessage());
							postWidget.getDataLabel().setText(temp.getCreated().toString());
							postWidget.getDeleteButton().addStyleDependentName("right");
							postWidget.getDeleteButton().setText(delete);
							postWidget.setStyleName("bordered");
							
							if (isAdmin) {
								postWidget.getDeleteButton().setVisible(true);
							}
							
							if (authorsWidgets.containsKey(temp.getAuthorId())) {
								authorsWidgets.get(temp.getAuthorId()).add(postWidget);
							}
							else {
								ArrayList<PostWidget> list = new ArrayList<PostWidget>();
								list.add(postWidget);
								authorsWidgets.put(temp.getAuthorId(), list);
							}
							
							widget.getPostsPanel().add(postWidget);
						}
						
						for (Integer temp: authorsWidgets.keySet()) {
							GWTForum.GWTFORUM_SERVICE.getUser(temp, usersCallback);
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
			
			@Override
			public void onFailure(Throwable caught) {
				LoadingPopup.getInstance().hide();
				InfoPopup.getInstance().setText(GWTForum.MESSAGES.unknownError());
				InfoPopup.getInstance().show();
			}
		};
		
		adminCallback = new AsyncCallback<ResponseRpc<Boolean>>() {

			@Override
			public void onFailure(Throwable caught) {
				LoadingPopup.getInstance().hide();
				InfoPopup.getInstance().setText(GWTForum.MESSAGES.unknownError());
				InfoPopup.getInstance().show();
			}

			@Override
			public void onSuccess(ResponseRpc<Boolean> result) {

				if (!result.isError()) {
					isAdmin = result.getResponse();
					GWTForum.GWTFORUM_SERVICE.getReplies(threadId, repliesCallback);
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
		
		usersCallback = new AsyncCallback<ResponseRpc<UserRpc>>() {
			
			@Override
			public void onSuccess(ResponseRpc<UserRpc> result) {
				
				if (!result.isError()) {
					
					for (PostWidget temp: authorsWidgets.get(result.getResponse().getId())) {
						temp.getUsernameLabel().setText(result.getResponse().getName());
						temp.getNumberOfPostsValue().setText("" + result.getResponse().getNumberOfPosts());
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
			
			@Override
			public void onFailure(Throwable caught) {
				LoadingPopup.getInstance().hide();
				InfoPopup.getInstance().setText(GWTForum.MESSAGES.unknownError());
				InfoPopup.getInstance().show();
			}
		};
	}
	
	public PostsWidget getWidget() {
		return widget;
	}
	
	public static void deletePost(int postId) {
		LoadingPopup.getInstance().show();
		GWTForum.GWTFORUM_SERVICE.deleteReply(postId, repliesCallback);
	}
	
	private ClickHandler closeDialogClickHandler = new ClickHandler() {
		
		@Override
		public void onClick(ClickEvent event) {
			InfoPopup.getInstance().hide();
		}
	};
	
	private ClickHandler newPostClickHandler = new ClickHandler() {
		
		@Override
		public void onClick(ClickEvent event) {
			ReplyRpc reply = new ReplyRpc();
			reply.setThreadId(threadId);
			reply.setMessage(widget.getNewPostText().getText());
			widget.getNewPostText().setText("");
			
			LoadingPopup.getInstance().show();
			GWTForum.GWTFORUM_SERVICE.saveReply(reply, repliesCallback);
		}
	};
}
