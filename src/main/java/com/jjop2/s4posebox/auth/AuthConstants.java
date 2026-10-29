package com.jjop2.s4posebox.auth;

import org.springframework.stereotype.Component;

@Component
public final class AuthConstants {
	
	// RefreshToken 만료시간: 30일 (초 단위)
	public static final long REFRESH_TOKEN_EXPIRATION_TIME_SECONDS = 30L * 24 * 60 * 60;
	
	public static final String REFRESH_TOKEN_COOKIE_NAME = "refreshToken";
	
	private AuthConstants() {}
}
