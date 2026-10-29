package com.jjop2.s4posebox.common;

public final class ApiPaths {
	
	// === Prefix ===
	public static final String USER = "/user";
	public static final String AUTH = "/auth";
	
	// === User API ===
	public static final String USER_SIGNUP = "/signup";
	
	// === Auth API ===
	public static final String AUTH_LOGIN = "/login";
	public static final String AUTH_REFRESH = "/refresh";
	public static final String AUTH_LOGOUT = "/logout";
	
	// === SecurityConfig ===
	public static final String USER_SIGNUP_FULL = USER + USER_SIGNUP;	// "/user/signup"
	public static final String AUTH_LOGIN_FULL = AUTH + AUTH_LOGIN;		// "/auth/login"
	public static final String AUTH_REFRESH_FULL = AUTH + AUTH_REFRESH;	// "/auth/refresh"
	public static final String AUTH_LOGOUT_FULL = AUTH + AUTH_LOGOUT;	// "/auth/logout"

	
	private ApiPaths() {}
}
