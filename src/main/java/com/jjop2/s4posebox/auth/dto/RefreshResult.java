package com.jjop2.s4posebox.auth.dto;

public record RefreshResult(
	
	RefreshResponse response,
	
	String refreshToken
	
) {}
