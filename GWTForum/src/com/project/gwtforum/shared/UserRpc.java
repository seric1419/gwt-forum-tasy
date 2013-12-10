package com.project.gwtforum.shared;

import com.google.gwt.user.client.rpc.IsSerializable;

public class UserRpc implements IsSerializable {

	private int id;
	private int numerOfPosts;
	private String name;
	
	public UserRpc() {
		
	}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getNumberOfPosts() {
		return numerOfPosts;
	}
	public void setNumerOfPosts(int numerOfPosts) {
		this.numerOfPosts = numerOfPosts;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
}
