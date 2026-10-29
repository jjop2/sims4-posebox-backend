package com.jjop2.s4posebox.auth.dto;

public record LoginResponse(
	
	String accessToken,
		
	UserDto user
	
) {}
