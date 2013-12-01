package com.project.gwtforum.client;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import com.project.gwtforum.shared.ResponseRpc;

@RemoteServiceRelativePath("index")
public interface GWTForumService extends RemoteService {

	public ResponseRpc<Boolean> register(String login, String password);
}
