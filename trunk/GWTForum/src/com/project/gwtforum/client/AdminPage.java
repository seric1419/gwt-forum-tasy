package com.project.gwtforum.client;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.project.gwtforum.client.widgets.AdminPanelWidget;
import com.project.gwtforum.shared.CategoryRpc;
import com.project.gwtforum.shared.ForumRpc;
import com.project.gwtforum.shared.ResponseRpc;

public class AdminPage {

	private final AdminPanelWidget widget = new AdminPanelWidget();
	
	private AsyncCallback<ResponseRpc<CategoryRpc>> categoriesCallback;
	private AsyncCallback<ResponseRpc<ForumRpc>> forumsCallback;
	
	public AdminPage() {
		initializeCallbacks();
		
		widget.getCategoryLabel().setText(GWTForum.CONSTANTS.category());
		widget.getForumLabel().setText(GWTForum.CONSTANTS.forum());
		widget.getNameLabel().setText(GWTForum.CONSTANTS.name());
		widget.getCategoryNameTextBox().addStyleDependentName("register");
		widget.getForumNameTextBox().addStyleDependentName("register");
		widget.getSectionLabel().setText(GWTForum.CONSTANTS.section());
		widget.getForumListBox().addStyleDependentName("wide");
		widget.getCategoryButton().setText(GWTForum.CONSTANTS.save());
		widget.getCategoryButton().addStyleDependentName("right");
		widget.getForumButton().setText(GWTForum.CONSTANTS.save());
		widget.getForumButton().addStyleDependentName("right");
		widget.getForumButton().setEnabled(false);
		
		widget.getCategoryButton().addClickHandler(categoryButtonClickHandler);
		widget.getForumButton().addClickHandler(forumButtonClickHandler);
		
		GWTForum.GWTFORUM_SERVICE.getCategories(categoriesCallback);
	}
	
	private void initializeCallbacks() {
		categoriesCallback = new AsyncCallback<ResponseRpc<CategoryRpc>>() {

			@Override
			public void onFailure(Throwable caught) {
				
			}

			@Override
			public void onSuccess(ResponseRpc<CategoryRpc> result) {
				widget.getCategoryNameTextBox().setText("");
				
				if (!result.isError()) {
					if (result.getResponseCollection().size() > 0){
						widget.getForumButton().setEnabled(true);
						widget.getForumListBox().clear();
						
						for (CategoryRpc temp: result.getResponseCollection()) {
							widget.getForumListBox().addItem(temp.getId() + " - " + temp.getName());
						}
					}
				}
				else {
					if (result.getErrorMessages().containsKey("notLoggedIn")) {
						History.newItem("index");
					}
				}
			}
		};
		
		forumsCallback = new AsyncCallback<ResponseRpc<ForumRpc>>() {

			@Override
			public void onFailure(Throwable caught) {
				
			}

			@Override
			public void onSuccess(ResponseRpc<ForumRpc> result) {
				widget.getForumNameTextBox().setText("");
			}
		};
	}
	
	public AdminPanelWidget getWidget() {
		return widget;
	}
	
	private ClickHandler categoryButtonClickHandler = new ClickHandler() {
		
		@Override
		public void onClick(ClickEvent event) {
			CategoryRpc category = new CategoryRpc();
			category.setName(widget.getCategoryNameTextBox().getText());
			
			GWTForum.GWTFORUM_SERVICE.saveCategory(category, categoriesCallback);
		}
	};
	
	private ClickHandler forumButtonClickHandler = new ClickHandler() {
		
		@Override
		public void onClick(ClickEvent event) {
			ForumRpc forum = new ForumRpc();
			forum.setName(widget.getForumNameTextBox().getText());
			
			String selectedValue = widget.getForumListBox().getValue(widget.getForumListBox().getSelectedIndex());
			int categoryId = Integer.parseInt(selectedValue.split(" - ")[0]);
			
			forum.setCategoryId(categoryId);
			
			GWTForum.GWTFORUM_SERVICE.saveForum(forum, forumsCallback);
		}
	};
}
