package com.project.gwtforum.client;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.project.gwtforum.shared.CategoryRpc;
import com.project.gwtforum.shared.ForumRpc;
import com.project.gwtforum.shared.ReplyRpc;
import com.project.gwtforum.shared.ResponseRpc;
import com.project.gwtforum.shared.ThreadRpc;
import com.project.gwtforum.shared.UserRpc;

public interface GWTForumServiceAsync {

	public void register(String login, String password, AsyncCallback<ResponseRpc<Boolean>> callback);
	public void login(String login, String password, AsyncCallback<ResponseRpc<Boolean>> callback);
	public void logout(AsyncCallback<ResponseRpc<Boolean>> callback);
	public void isAdmin(AsyncCallback<ResponseRpc<Boolean>> callback);
	public void getUser(int userId, AsyncCallback<ResponseRpc<UserRpc>> callback);
	
	public void getReplies(int id, AsyncCallback<ResponseRpc<ReplyRpc>> callback);
	public void saveReply(ReplyRpc newReply, AsyncCallback<ResponseRpc<ReplyRpc>> callback);
	public void deleteReply(int replyId, AsyncCallback<ResponseRpc<ReplyRpc>> callback);
	public void getThreads(int id, AsyncCallback<ResponseRpc<ThreadRpc>> callback);
	public void saveThread(ThreadRpc newThread, AsyncCallback<ResponseRpc<ThreadRpc>> callback);
	public void getForums(int id, AsyncCallback<ResponseRpc<ForumRpc>> callback);
	public void saveForum(ForumRpc newForum, AsyncCallback<ResponseRpc<ForumRpc>> callback);
	public void getCategories(AsyncCallback<ResponseRpc<CategoryRpc>> callback);
	public void saveCategory(CategoryRpc newCategory, AsyncCallback<ResponseRpc<CategoryRpc>> callback);
}
