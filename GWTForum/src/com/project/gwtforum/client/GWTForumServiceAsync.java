package com.project.gwtforum.client;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.project.gwtforum.shared.ResponseRpc;

public interface GWTForumServiceAsync {

	public void register(String login, String password, AsyncCallback<ResponseRpc<Boolean>> callback);
}
