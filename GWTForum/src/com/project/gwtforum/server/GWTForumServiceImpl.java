package com.project.gwtforum.server;

import java.sql.SQLException;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.project.gwtforum.client.GWTForumService;
import com.project.gwtforum.server.database.BCrypt;
import com.project.gwtforum.server.database.Database;
import com.project.gwtforum.server.database.Role;
import com.project.gwtforum.server.database.User;
import com.project.gwtforum.shared.ResponseRpc;

public class GWTForumServiceImpl extends RemoteServiceServlet implements GWTForumService{

	private static final long serialVersionUID = 669987357819776292L;

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
}
