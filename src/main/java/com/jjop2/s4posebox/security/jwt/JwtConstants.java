package com.jjop2.s4posebox.security.jwt;

import org.springframework.stereotype.Component;

@Component
public final class JwtConstants {
	
	// AccessToken 만료시간: 1시간 (밀리초 단위)
	public static final long ACCESS_TOKEN_EXPIRATION_TIME_MILLIS = 60 * 60 * 1000L; 

	public static final String TOKEN_PREFIX = "Bearer ";
	
	public static final String ROLE_CLAIM = "role";
	
	public static final String ACCOUNT_TYPE_CLAIM = "accountType";
	
	private JwtConstants() {}
}
