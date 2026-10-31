package com.jjop2.s4posebox.auth.dto;

public record RefreshResponse(
	
	String accessToken,
	
	UserDto user
	
) {}
