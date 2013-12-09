package com.project.gwtforum.client;

import java.util.ArrayList;
import java.util.HashMap;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.project.gwtforum.client.widgets.CategoriesWidget;
import com.project.gwtforum.client.widgets.CategoryWidget;
import com.project.gwtforum.client.widgets.ForumWidget;
import com.project.gwtforum.client.widgets.InfoPopup;
import com.project.gwtforum.client.widgets.LoadingPopup;
import com.project.gwtforum.shared.CategoryRpc;
import com.project.gwtforum.shared.ForumRpc;
import com.project.gwtforum.shared.ResponseRpc;

public class CategoriesPage {

	private final CategoriesWidget widget = new CategoriesWidget();
	
	private AsyncCallback<ResponseRpc<CategoryRpc>> categoriesCallback;
	private AsyncCallback<ResponseRpc<ForumRpc>> forumsCallback;
	
	private ArrayList<CategoryRpc> categories;
	private HashMap<Integer, CategoryWidget> widgetsMap = new HashMap<Integer, CategoryWidget>();
	private int currentCategory = 0;
	
	public CategoriesPage() {
		initializeCallbacks();
		
		InfoPopup.getInstance().setButtonClickHandler(closeDialogClickHandler);
		LoadingPopup.getInstance().show();
		GWTForum.GWTFORUM_SERVICE.getCategories(categoriesCallback);
	}
	
	private void initializeCallbacks() {
		categoriesCallback = new AsyncCallback<ResponseRpc<CategoryRpc>>() {

			@Override
			public void onFailure(Throwable caught) {
				LoadingPopup.getInstance().hide();
				InfoPopup.getInstance().setText(GWTForum.MESSAGES.unknownError());
				InfoPopup.getInstance().center();
			}

			@Override
			public void onSuccess(ResponseRpc<CategoryRpc> result) {
				
				if (!result.isError()) {
					categories = result.getResponseCollection();
					
					if (categories.size() > 0) {
						
						for (CategoryRpc temp: categories) {
							CategoryWidget categoryWidget = new CategoryWidget();
							categoryWidget.getCategoryTitle().setText(temp.getName());
							categoryWidget.getCategoryTitle().addStyleName("categoryHeader");
							
							widgetsMap.put(temp.getId(), categoryWidget);
							widget.getCategoriesPanel().add(categoryWidget);
						}
						
						GWTForum.GWTFORUM_SERVICE.getForums(categories.get(0).getId(), forumsCallback);
					}
					else {
						LoadingPopup.getInstance().hide();
					}
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
		
		forumsCallback = new AsyncCallback<ResponseRpc<ForumRpc>>() {

			@Override
			public void onFailure(Throwable caught) {
				LoadingPopup.getInstance().hide();
				InfoPopup.getInstance().setText(GWTForum.MESSAGES.unknownError());
				InfoPopup.getInstance().center();
			}

			@Override
			public void onSuccess(ResponseRpc<ForumRpc> result) {
				
				if(!result.isError()) {
					
					if (result.getResponseCollection().size() > 0) {
						
						for (ForumRpc temp: result.getResponseCollection()) {
							ForumWidget forumWidget = new ForumWidget(temp.getId());
							forumWidget.getForumLink().setText(temp.getName());
							forumWidget.addStyleName("bordered");
							
							widgetsMap.get(categories.get(currentCategory).getId()).getCategoryPanel().add(forumWidget);
						}
					}
					
					if (currentCategory < categories.size() - 1) {
						GWTForum.GWTFORUM_SERVICE.getForums(categories.get(++currentCategory).getId(), forumsCallback);
					}
					else {
						LoadingPopup.getInstance().hide();
					}
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
	
	public CategoriesWidget getWidget() {
		return widget;
	}
	
	private ClickHandler closeDialogClickHandler = new ClickHandler() {
		
		@Override
		public void onClick(ClickEvent event) {
			InfoPopup.getInstance().hide();
		}
	};
}
