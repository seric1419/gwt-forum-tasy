package com.project.gwtforum.server.database;

public enum Role {

	Administrator(0),
	Moderator(1),
	User(2);
	
	private int type;
	
	Role(int type) {
		this.type = type;
	}
	
	public int getType() {
		return this.type;
	}
}
