package com.netease.cloud.nsf.demo.stock.caller.web.auth;

import com.netease.cloud.nsf.demo.stock.caller.web.entity.AuthToken;

/**
 * @author Chen Jiahan | chenjiahan@corp.netease.com
 */
public class AuthManager {

	private static AuthToken authToken = null;
	
	public static void setAuthToken(AuthToken token) {
		authToken = token;
	}
	
	public static AuthToken getAuthToken() {
		return authToken;
	}
	
	public static void clearToken() {
		authToken = null;
	}
	
	public static boolean isEmpty() {
		return authToken == null;
	}
}
