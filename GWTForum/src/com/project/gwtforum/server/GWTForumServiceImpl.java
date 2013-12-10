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
import com.project.gwtforum.shared.UserRpc;

public class GWTForumServiceImpl extends RemoteServiceServlet implements GWTForumService{

	private static final long serialVersionUID = 669987357819776292L;

	public ResponseRpc<ReplyRpc> getReplies(int id){
		ResponseRpc<ReplyRpc> response = new ResponseRpc<>();
		
		if (isLoggedIn()) {
			ArrayList<ReplyRpc> responseCollection = new ArrayList<ReplyRpc>();
			
			try {
				List<Reply> dbReplies = Database.getInstance().getRepliesDao().queryBuilder().where().eq("threadId", id).query();
				
				if (dbReplies != null) {
					for(Reply i : dbReplies){
						ReplyRpc reply = new ReplyRpc();
						reply.setId(i.getId());
						reply.setAuthorId(i.getAuthorId());
						reply.setName(i.getName());
						reply.setMessage(i.getMessage());
						reply.setCreated(i.getCreated());
						responseCollection.add(reply);
					}
					response.setResponseCollection(responseCollection);
				}
				else {
					response.setResponseCollection(new ArrayList<ReplyRpc>());
				}
			} catch (SQLException e) {
				response.setError(true);
				response.addErrorMessage("unknown", e.getMessage());
			}
		}
		else {
			response.setError(true);
			response.addErrorMessage("notLoggedIn", "");
		}
		
		return response;
	}
	
	@Override
	public ResponseRpc<ReplyRpc> saveReply(ReplyRpc newReply) {
		ResponseRpc<ReplyRpc> response = new ResponseRpc<>();
		
		if (isLoggedIn()) {
			Reply reply = new Reply();
			
			reply.setName(newReply.getName());
			reply.setThreadId(newReply.getThreadId());
			reply.setAuthorId(SessionManager.getInstance().getUserIdFromSessionId((String) getThreadLocalRequest().getSession().getAttribute("sessionId")));
			reply.setMessage(newReply.getMessage());
			
			try {
				Database.getInstance().getRepliesDao().create(reply);
				
				response.setResponseCollection(getReplies(newReply.getThreadId()).getResponseCollection());
			} catch (SQLException e) {
				response.setError(true);
				response.addErrorMessage("unknown", e.getMessage());
			}
		}
		else {
			response.setError(true);
			
			if (isLoggedIn()) {
				response.addErrorMessage("noPermissions", "");
			}
			else {
				response.addErrorMessage("notLoggedIn", "");
			}
		}
		
		return response;
	}
	
	@Override
	public ResponseRpc<ReplyRpc> deleteReply(int replyId) {
		ResponseRpc<ReplyRpc> response = new ResponseRpc<>();
		
		if (isAdmin().getResponse()) {
			try {
				int threadId = Database.getInstance().getRepliesDao().queryForId(replyId).getThreadId();
				
				Database.getInstance().getRepliesDao().deleteById(replyId);
				
				response.setResponseCollection(getReplies(threadId).getResponseCollection());
			} catch (SQLException e) {
				response.setError(true);
				response.addErrorMessage("unknown", e.getMessage());
			}
		}
		else {
			response.setError(true);
			
			if (isLoggedIn()) {
				response.addErrorMessage("noPermissions", "");
			}
			else {
				response.addErrorMessage("notLoggedIn", "");
			}
		}
		
		return response;
	}
	
	public ResponseRpc<ThreadRpc> getThreads(int id){
		ResponseRpc<ThreadRpc> response = new ResponseRpc<>();
		
		if(isLoggedIn()) {
			ArrayList<ThreadRpc> responseCollection = new ArrayList<ThreadRpc>();
			
			try {
				List<Thread> dbThreads = Database.getInstance().getThreadsDao().queryBuilder().where().eq("forumId", id).query();
				
				if (dbThreads != null) {
					for(Thread i : dbThreads){
						ThreadRpc thread = new ThreadRpc();
						thread.setId(i.getId());
						thread.setName(i.getName());
						responseCollection.add(thread);
					}
					response.setResponseCollection(responseCollection);
				}
				else {
					response.setResponseCollection(new ArrayList<ThreadRpc>());
				}
			} catch (SQLException e) {
				response.setError(true);
				response.addErrorMessage("unknown", e.getMessage());
			}
		}
		else {
			response.setError(true);
			response.addErrorMessage("notLoggedIn", "");
		}
		
		return response;
	}
	
	@Override
	public ResponseRpc<ThreadRpc> saveThread(ThreadRpc newThread) {
		ResponseRpc<ThreadRpc> response = new ResponseRpc<>();
		
		if (isAdmin().getResponse()) {
			Thread thread = new Thread();
			
			thread.setName(newThread.getName());
			thread.setForumId(newThread.getForumId());
			thread.setAuthorId(SessionManager.getInstance().getUserIdFromSessionId((String) getThreadLocalRequest().getSession().getAttribute("sessionId")));
			
			try {
				Database.getInstance().getThreadsDao().create(thread);
				
				response.setResponseCollection(getThreads(newThread.getForumId()).getResponseCollection());
			} catch (SQLException e) {
				response.setError(true);
				response.addErrorMessage("unknown", e.getMessage());
			}
		}
		else {
			response.setError(true);
			
			if (isLoggedIn()) {
				response.addErrorMessage("noPermissions", "");
			}
			else {
				response.addErrorMessage("notLoggedIn", "");
			}
		}
		
		return response;
	}
	
	public ResponseRpc<ForumRpc> getForums(int id){
		ResponseRpc<ForumRpc> response = new ResponseRpc<>();
		
		if (isLoggedIn()) {
			ArrayList<ForumRpc> responseCollection = new ArrayList<ForumRpc>();
			
			try {
				List<Forum> dbForums = Database.getInstance().getForumsDao().queryBuilder().where().eq("categoryId", id).query();
				
				if (dbForums != null) {
					for(Forum i : dbForums){
						ForumRpc forum = new ForumRpc();
						forum.setId(i.getId());
						forum.setName(i.getName());
						responseCollection.add(forum);
					}
					response.setResponseCollection(responseCollection);
				}
				else {
					response.setResponseCollection(new ArrayList<ForumRpc>());
				}
			} catch (SQLException e) {
				response.setError(true);
				response.addErrorMessage("unknown", e.getMessage());
			}
		}
		else {
			response.setError(true);
			response.addErrorMessage("notLoggedIn", "");
		}
		
		return response;
	}
	
	@Override
	public ResponseRpc<ForumRpc> saveForum(ForumRpc newForum) {
		ResponseRpc<ForumRpc> response = new ResponseRpc<ForumRpc>();
		
		if (isAdmin().getResponse()) {
			Forum forum = new Forum();
			
			forum.setName(newForum.getName());
			forum.setCategoryId(newForum.getCategoryId());
			
			try {
				Database.getInstance().getForumsDao().create(forum);
				
				response.setResponseCollection(getForums(newForum.getCategoryId()).getResponseCollection());
			} catch (SQLException e) {
				response.setError(true);
				response.addErrorMessage("unknown", e.getMessage());
			}
		}
		else {
			response.setError(true);
			
			if (isLoggedIn()) {
				response.addErrorMessage("noPermissions", "");
			}
			else {
				response.addErrorMessage("notLoggedIn", "");
			}
		}
		
		return response;
	}
	
	public ResponseRpc<CategoryRpc> getCategories(){
		ResponseRpc<CategoryRpc> response = new ResponseRpc<>();
		
		if (isLoggedIn()) {
			ArrayList<CategoryRpc> responseCollection = new ArrayList<CategoryRpc>();
			
			try {
				List<Category> dbCategories = Database.getInstance().getCategoriesDao().queryForAll();
				
				if (dbCategories != null) {
					for(Category i : dbCategories){
						CategoryRpc category = new CategoryRpc();
						category.setId(i.getId());
						category.setName(i.getName());
						responseCollection.add(category);
					}
					response.setResponseCollection(responseCollection);
				}
				else {
					response.setResponseCollection(new ArrayList<CategoryRpc>());
				}
			} catch (SQLException e) {
				response.setError(true);
				response.addErrorMessage("unknown", e.getMessage());
			}
		}
		else {
			response.setError(true);
			response.addErrorMessage("notLoggedIn", "");
		}
		
		
		return response;
	}
	
	@Override
	public ResponseRpc<CategoryRpc> saveCategory(CategoryRpc newCategory) {
		ResponseRpc<CategoryRpc> response = new ResponseRpc<CategoryRpc>();
		
		if (isAdmin().getResponse()) {
			Category category = new Category();
			
			category.setName(newCategory.getName());
			
			try {
				Database.getInstance().getCategoriesDao().create(category);
				
				response.setResponseCollection(getCategories().getResponseCollection());
			} catch (SQLException e) {
				response.setError(true);
				response.addErrorMessage("unknown", e.getMessage());
			}
		}
		else {
			response.setError(true);
			
			if (isLoggedIn()) {
				response.addErrorMessage("noPermissions", "");
			}
			else {
				response.addErrorMessage("notLoggedIn", "");
			}
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
	
	@Override
	public ResponseRpc<Boolean> isAdmin() {
		ResponseRpc<Boolean> response = new ResponseRpc<Boolean>();
		
		if (isLoggedIn()) {
			HttpServletRequest request = this.getThreadLocalRequest();
			HttpSession session = request.getSession();
			
			String sessionId = (String)session.getAttribute("sessionId");
			
			int userId = SessionManager.getInstance().getUserIdFromSessionId(sessionId);
			
			try {
				User user = Database.getInstance().getUsersDao().queryForId(userId);
				
				if (user.getRole() == Role.Administrator) {
					response.setResponse(true);
				}
				else {
					response.setResponse(false);
				}
			} catch (SQLException e) {
				response.setError(true);
				response.addErrorMessage("unknown", e.getMessage());
				response.setResponse(false);
			}
		}
		else {
			response.setError(true);
			response.addErrorMessage("notLoggedIn", "");
			response.setResponse(false);
		}
		
		return response;
	}
	
	@Override
	public ResponseRpc<UserRpc> getUser(int userId) {
		ResponseRpc<UserRpc> response = new ResponseRpc<>();
		
		try {
			UserRpc user = new UserRpc();
			user.setNumerOfPosts(Database.getInstance().getRepliesDao().queryBuilder().where().eq("authorId", userId).query().size());
			user.setName(Database.getInstance().getUsersDao().queryForId(userId).getLogin());
			user.setId(userId);
			
			response.setResponse(user);
		} catch (SQLException e) {
			response.setError(true);
			response.addErrorMessage("unknown", e.getMessage());
		}
		
		return response;
	}
	
	private boolean isLoggedIn() {
		HttpServletRequest request = this.getThreadLocalRequest();
		HttpSession session = request.getSession(false);
		
		if (session != null) {
			String sessionId = (String)session.getAttribute("sessionId");
			
			if (sessionId != null) {
				if (SessionManager.getInstance().isViable(sessionId)) {
					return true;
				}
				return false;
			}
			return false;
		}
		return false;
	}
}
