package com.jjop2.s4posebox.user.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class LoginRequest {
	
	@NotBlank(message = "Login ID is required.")
	private String loginId;
	
	@NotBlank(message = "Password is required.")
	private String password;

}
