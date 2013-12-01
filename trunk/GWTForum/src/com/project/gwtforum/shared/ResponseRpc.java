package com.project.gwtforum.shared;

import java.util.ArrayList;

import com.google.gwt.user.client.rpc.IsSerializable;

public class ResponseRpc<T> implements IsSerializable{

	private boolean error;
	private String errorMessage;
	private T response;
	private ArrayList<T> responseCollection;
	
	public ResponseRpc() {
		this.error = false;
		this.errorMessage = "";
	}
	
	public ResponseRpc(T response) {
		this.error = false;
		this.errorMessage = "";
		this.response = response;
	}
	
	public ResponseRpc(ArrayList<T> responseCollection) {
		this.error = false;
		this.errorMessage = "";
		this.responseCollection = responseCollection;
	}

	public boolean isError() {
		return error;
	}

	public void setError(boolean error) {
		this.error = error;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	public T getResponse() {
		return response;
	}

	public void setResponse(T response) {
		this.response = response;
	}

	public ArrayList<T> getResponseCollection() {
		return responseCollection;
	}

	public void setResponseCollection(ArrayList<T> responseCollection) {
		this.responseCollection = responseCollection;
	}
}
