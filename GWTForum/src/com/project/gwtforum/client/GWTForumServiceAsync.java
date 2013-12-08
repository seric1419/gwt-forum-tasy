package com.project.gwtforum.client;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.project.gwtforum.shared.CategoryRpc;
import com.project.gwtforum.shared.ForumRpc;
import com.project.gwtforum.shared.ReplyRpc;
import com.project.gwtforum.shared.ResponseRpc;
import com.project.gwtforum.shared.ThreadRpc;

public interface GWTForumServiceAsync {

	public void register(String login, String password, AsyncCallback<ResponseRpc<Boolean>> callback);

	void getReplies(int id, AsyncCallback<ResponseRpc<ReplyRpc>> callback);
	
	void getThreads(int id, AsyncCallback<ResponseRpc<ThreadRpc>> callback);

	void getForums(int id, AsyncCallback<ResponseRpc<ForumRpc>> callback);

	void getCategories(AsyncCallback<ResponseRpc<CategoryRpc>> callback);
}
