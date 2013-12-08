package com.project.gwtforum.server;

import java.util.HashMap;
import java.util.Iterator;

public class SessionManager {

	private HashMap<String, Integer> userSessions;
	
	private static SessionManager instance = new SessionManager();
	
	private SessionManager() {
		userSessions = new HashMap<String, Integer>();
	}
	
	public static SessionManager getInstance() {
		if (instance == null) {
			instance = new SessionManager();
		}
		return instance;
	}
	
	public HashMap<String, Integer> getSessions() {
		return userSessions;
	}
	
	public String addSessionForUser(int userId) {
		if (isLoggedIn(userId)) {
			String key = "";
			
			Iterator<String> iterator = userSessions.keySet().iterator(); 
			
			if (iterator.hasNext()) {
				key = iterator.next();
			}
			
			while (!(userSessions.get(key) == userId)) {
				key = iterator.next();
			}
			
			removeSession(key);
		}
		
		String sessionId = RandomUtils.getRandomString(32);
		userSessions.put(sessionId, userId);
		
		return sessionId;
	}
	
	public boolean isViable(String sessionId) {
		return userSessions.containsKey(sessionId);
	}
	
	public void removeSession(String sessionId) {
		userSessions.remove(sessionId);
	}
	
	public int getUserIdFromSessionId(String sessionId) {
		if (!isViable(sessionId)) {
			return -1;
		}
		return userSessions.get(sessionId);
	}
	
	public boolean isLoggedIn(int userId) {
		return userSessions.containsValue(userId);
	}
}
