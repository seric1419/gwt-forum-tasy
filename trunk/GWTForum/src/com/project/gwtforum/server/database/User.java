package com.project.gwtforum.server.database;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "users")
public class User {

	@DatabaseField(generatedId = true)
	private int id;
	
	@DatabaseField(canBeNull = false)
	private String login;
	
	@DatabaseField(columnName = "password_hash", canBeNull = false)
	private String password;
	
	@DatabaseField(canBeNull = false)
	private Role role;
	
	@DatabaseField
	int postsNumber;
	
	public User() {
		
	}
	
	public User(String login, String passwordHash) {
		this.login = login;
		this.password = passwordHash;
		this.role = Role.User;
		postsNumber = 0;
	}
	
	public User(String login, String passwordHash, Role role) {
		this.login = login;
		this.password = passwordHash;
		this.role = role;
		postsNumber = 0;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}
	
	public int getPostsNumber(){
		return postsNumber;
	}
	
	public void setPostsNumber(int postsNumber) {
		this.postsNumber = postsNumber;
	}
}
