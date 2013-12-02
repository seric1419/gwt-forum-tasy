package com.project.gwtforum.shared;

import java.util.ArrayList;
import java.util.HashMap;

import com.google.gwt.user.client.rpc.IsSerializable;

public class ResponseRpc<T> implements IsSerializable{

	private boolean error;
	private HashMap<String, String> errorMessages;
	private T response;
	private ArrayList<T> responseCollection;
	
	public ResponseRpc() {
		this.error = false;
		this.errorMessages = new HashMap<String, String>();
	}
	
	public ResponseRpc(T response) {
		this.error = false;
		this.errorMessages = new HashMap<String, String>();
		this.response = response;
	}
	
	public ResponseRpc(ArrayList<T> responseCollection) {
		this.error = false;
		this.errorMessages = new HashMap<String, String>();
		this.responseCollection = responseCollection;
	}

	public boolean isError() {
		return error;
	}

	public void setError(boolean error) {
		this.error = error;
	}

	public HashMap<String, String> getErrorMessages() {
		return errorMessages;
	}

	public void setErrorMessages(HashMap<String, String> errorMessages) {
		this.errorMessages = errorMessages;
	}
	
	public void addErrorMessage(String errorTag, String errorMessage) {
		this.errorMessages.put(errorTag, errorMessage);
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
