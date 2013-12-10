package com.project.gwtforum.shared;

import java.util.Date;

import com.google.gwt.user.client.rpc.IsSerializable;

public class ReplyRpc implements IsSerializable {

	private int id;
	private String name;
	private int authorId;
	private int threadId;
	private String message;
	private Date created;
	
	public ReplyRpc() {
		
	}
	
	public ReplyRpc(int id, String name, int threadId, int authorId, String message) {
		this.id = id;
		this.name = name;
		this.threadId = threadId;
		this.authorId = authorId;
		this.message = message;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getAuthorId() {
		return authorId;
	}

	public void setAuthorId(int authorId) {
		this.authorId = authorId;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getThreadId() {
		return threadId;
	}

	public void setThreadId(int threadId) {
		this.threadId = threadId;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Date getCreated() {
		return created;
	}

	public void setCreated(Date created) {
		this.created = created;
	}
}
