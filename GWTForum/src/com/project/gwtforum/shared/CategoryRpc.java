package com.project.gwtforum.shared;

import com.google.gwt.user.client.rpc.IsSerializable;

public class CategoryRpc implements IsSerializable {
	
	private int id;
	private String name;
	
	public CategoryRpc() {
		
	}
	
	public CategoryRpc(int id, String name) {
		this.id = id;
		this.name = name;
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
