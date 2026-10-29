package com.jjop2.s4posebox.auth.dto;

import com.jjop2.s4posebox.user.domain.AccountType;
import com.jjop2.s4posebox.user.domain.Role;

public record UserDto(
	
	Long id,
	
	Role role,
	
	AccountType accountType,
	
	String nickname
	
) {}
