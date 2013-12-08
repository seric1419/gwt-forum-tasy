package com.project.gwtforum.server;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.project.gwtforum.client.GWTForumService;
import com.project.gwtforum.server.database.BCrypt;
import com.project.gwtforum.server.database.Category;
import com.project.gwtforum.server.database.Database;
import com.project.gwtforum.server.database.Forum;
import com.project.gwtforum.server.database.Thread;
import com.project.gwtforum.server.database.Reply;
import com.project.gwtforum.server.database.Role;
import com.project.gwtforum.server.database.User;
import com.project.gwtforum.shared.CategoryRpc;
import com.project.gwtforum.shared.ForumRpc;
import com.project.gwtforum.shared.ReplyRpc;
import com.project.gwtforum.shared.ResponseRpc;
import com.project.gwtforum.shared.ThreadRpc;

public class GWTForumServiceImpl extends RemoteServiceServlet implements GWTForumService{

	private static final long serialVersionUID = 669987357819776292L;

	public ResponseRpc<ReplyRpc> getReplies(int id){
		
		ReplyRpc reply = new ReplyRpc();
		ArrayList<ReplyRpc> responseCollection = new ArrayList<ReplyRpc>();
		ResponseRpc<ReplyRpc> response = new ResponseRpc<>();
		
		try {
			List<Reply> dbReplies = Database.getInstance().getRepliesDao().queryBuilder().where().eq("threadId", id).query();
			for(Reply i : dbReplies){
				reply.setId(i.getId());
				reply.setName(i.getName());
				responseCollection.add(reply);
			}
			response.setResponseCollection(responseCollection);
		} catch (SQLException e) {
			response.setError(true);
			response.addErrorMessage("unknown", e.getMessage());
		}
		
		return response;
	}
	
	public ResponseRpc<ThreadRpc> getThreads(int id){
		
		ThreadRpc thread = new ThreadRpc();
		ArrayList<ThreadRpc> responseCollection = new ArrayList<ThreadRpc>();
		ResponseRpc<ThreadRpc> response = new ResponseRpc<>();
		
		try {
			List<Thread> dbThreads = Database.getInstance().getThreadsDao().queryBuilder().where().eq("forumId", id).query();
			for(Thread i : dbThreads){
				thread.setId(i.getId());
				thread.setName(i.getName());
				responseCollection.add(thread);
			}
			response.setResponseCollection(responseCollection);
		} catch (SQLException e) {
			response.setError(true);
			response.addErrorMessage("unknown", e.getMessage());
		}
		
		return response;
	}
	
	public ResponseRpc<ForumRpc> getForums(int id){
		
		ForumRpc forum = new ForumRpc();
		ArrayList<ForumRpc> responseCollection = new ArrayList<ForumRpc>();
		ResponseRpc<ForumRpc> response = new ResponseRpc<>();
		
		try {
			List<Forum> dbForums = Database.getInstance().getForumsDao().queryBuilder().where().eq("categoryId", id).query();
			for(Forum i : dbForums){
				forum.setId(i.getId());
				forum.setName(i.getName());
				responseCollection.add(forum);
			}
			response.setResponseCollection(responseCollection);
		} catch (SQLException e) {
			response.setError(true);
			response.addErrorMessage("unknown", e.getMessage());
		}
		
		return response;
	}
	
	public ResponseRpc<CategoryRpc> getCategories(){
		
		CategoryRpc category = new CategoryRpc();
		ArrayList<CategoryRpc> responseCollection = new ArrayList<CategoryRpc>();
		ResponseRpc<CategoryRpc> response = new ResponseRpc<>();
		
		try {
			List<Category> dbCategories = Database.getInstance().getCategoriesDao().queryForAll();
			for(Category i : dbCategories){
				category.setId(i.getId());
				category.setName(i.getName());
				responseCollection.add(category);
			}
			response.setResponseCollection(responseCollection);
		} catch (SQLException e) {
			response.setError(true);
			response.addErrorMessage("unknown", e.getMessage());
		}
		
		return response;
	}
	
	
	@Override
	public ResponseRpc<Boolean> register(String login, String password){
		ResponseRpc<Boolean> response = new ResponseRpc<Boolean>();
		
		if (!login.matches("^[a-z0-9_.-]{3,15}$")) {
			String tag = "loginValidationError";
			
			if (login.length() < 3) {
				response.addErrorMessage(tag, "tooShort");
			}
			else if (login.length() > 15) {
				response.addErrorMessage(tag, "tooLong");
			}
			else {
				response.addErrorMessage(tag, "excludedCharacters");
			}
			
			response.setError(true);
			response.setResponse(false);
		}
		if (password.length() < 8) {
			response.setError(true);
			response.addErrorMessage("passwordValidationError", "");
			response.setResponse(false);
		}
		try {
			if (Database.getInstance().getUsersDao().queryBuilder().where().eq("login", login).query().size() > 0) {
				response.setError(true);
				response.addErrorMessage("loginInUse", "");
				response.setResponse(false);
			}
		} catch (SQLException e1) {
			response.setError(true);
			response.addErrorMessage("unknown", e1.getMessage());
			response.setResponse(false);
		}
		
		Role role = Role.User;
		
		if (!response.isError()) {
			try {
				if (Database.getInstance().getUsersDao().countOf() == 0) {
					role = Role.Administrator;
				}
				
				User user = new User(login, BCrypt.hashpw(password, BCrypt.gensalt()), role);
				
				Database.getInstance().getUsersDao().create(user);
			} catch (SQLException e) {
				response.setError(true);
				response.addErrorMessage("unknown", e.getMessage());
				response.setResponse(false);
			}
		}
		
		return response;
	}
	
	@Override
	public ResponseRpc<Boolean> login(String login, String password) {
		ResponseRpc<Boolean> response = new ResponseRpc<Boolean>();
		
		try {
			List<User> users = Database.getInstance().getUsersDao().queryBuilder().where().eq("login", login).query();
			
			if (users.size() == 0) {
				response.setError(true);
				response.addErrorMessage("wrongLogin", "");
				response.setResponse(false);
			}
			
			if (!response.isError()) {
				User user = users.get(0);
				
				if (BCrypt.checkpw(password, user.getPassword())) {
					String sessionId = SessionManager.getInstance().addSessionForUser(user.getId());
					
					HttpServletRequest request = this.getThreadLocalRequest();
					HttpSession httpSession = request.getSession(true);
					httpSession.setAttribute("sessionId", sessionId);
					
					response.setResponse(true);
				}
				else {
					response.setError(true);
					response.addErrorMessage("wrongPassword", "");
					response.setResponse(false);
				}
			}
			
		} catch (SQLException e) {
			response.setError(true);
			response.addErrorMessage("unknown", e.getMessage());
			response.setResponse(false);
		}
		
		return response;
	}
	
	@Override
	public ResponseRpc<Boolean> logout() {
		ResponseRpc<Boolean> response = new ResponseRpc<Boolean>();
		
		HttpServletRequest request = this.getThreadLocalRequest();
		HttpSession httpSession = request.getSession(true);
		
		String sessionId = (String)httpSession.getAttribute("sessionId");
		SessionManager.getInstance().removeSession(sessionId);
		httpSession.removeAttribute("sessionId");
		
		response.setResponse(true);
		
		return response;
	}
}
