package com.project.gwtforum.server.database;

import java.util.Date;

import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "replies")
public class Reply {
	
	
	@DatabaseField(generatedId = true)
	private int id;
	
	@DatabaseField
	private String name;
	
	@DatabaseField(canBeNull = false)
	private int authorId;
	
	@DatabaseField(canBeNull = false)
	private int threadId;

	@DatabaseField(canBeNull = false)
	private String message;
	
	@DatabaseField(dataType = DataType.DATE)
	private Date created;
	
	public Reply() {
		created = new Date();
	}
	
	public Reply(String name, int threadId, int authorId, String message) {
		this.name = name;
		this.threadId = threadId;
		this.authorId = authorId;
		this.message = message;
		created = new Date();
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
