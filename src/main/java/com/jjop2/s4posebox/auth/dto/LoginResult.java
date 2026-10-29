package com.jjop2.s4posebox.auth.dto;

public record LoginResult(
	
	LoginResponse response,
	
	String refreshToken
	
) {}
