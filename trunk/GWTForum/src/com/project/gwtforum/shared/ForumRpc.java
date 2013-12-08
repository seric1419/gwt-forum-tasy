package com.project.gwtforum.shared;

import com.google.gwt.user.client.rpc.IsSerializable;

public class ForumRpc implements IsSerializable {

	private int id;
	private String name;
	private String about;
	private int categoryId;
	
	public ForumRpc() {
		
	}
	
	public ForumRpc(int id, String name, String about, int categoryId) {
		this.id = id;
		this.name = name;
		this.about = about;
		this.categoryId = categoryId;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAbout() {
		return about;
	}

	public void setAbout(String about) {
		this.about = about;
	}

	public int getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(int categoryId) {
		this.categoryId = categoryId;
	}
	
}
