package com.project.gwtforum.shared;

import com.google.gwt.user.client.rpc.IsSerializable;

public class ThreadRpc implements IsSerializable {


	private int id;
	private String name;
	private int authorId;
	private int forumId;	
	
	public ThreadRpc() {
		
	}
	
	public ThreadRpc(int id, String name, int forumId, int authorId) {
		this.name = name;
		this.forumId = forumId;
		this.authorId = authorId;
	}

	public int getAuthorId() {
		return authorId;
	}

	public void setAuthorId(int authorId) {
		this.authorId = authorId;
	}

	public int getForumId() {
		return forumId;
	}

	public void setForumId(int forumId) {
		this.forumId = forumId;
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
	
}
