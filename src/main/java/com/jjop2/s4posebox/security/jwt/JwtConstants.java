package com.jjop2.s4posebox.security.jwt;

import org.springframework.stereotype.Component;

@Component
public final class JwtConstants {

	public static final String TOKEN_PREFIX = "Bearer ";
	
	public static final String ROLE_CLAIM = "role";
	
	private JwtConstants() {}
}
