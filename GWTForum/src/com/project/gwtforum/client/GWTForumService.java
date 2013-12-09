package com.project.gwtforum.client;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import com.project.gwtforum.shared.CategoryRpc;
import com.project.gwtforum.shared.ForumRpc;
import com.project.gwtforum.shared.ReplyRpc;
import com.project.gwtforum.shared.ResponseRpc;
import com.project.gwtforum.shared.ThreadRpc;

@RemoteServiceRelativePath("index")
public interface GWTForumService extends RemoteService {

	public ResponseRpc<Boolean> register(String login, String password);
	public ResponseRpc<Boolean> login(String login, String password);
	public ResponseRpc<Boolean> logout();
	public ResponseRpc<Boolean> isAdmin();
	
	public ResponseRpc<ReplyRpc> getReplies(int id);
	public ResponseRpc<ThreadRpc> getThreads(int id);
	public ResponseRpc<ForumRpc> getForums(int id);
	public ResponseRpc<ForumRpc> saveForum(ForumRpc newForum);
	public ResponseRpc<CategoryRpc> getCategories();
	public ResponseRpc<CategoryRpc> saveCategory(CategoryRpc newCategory);
}
