package com.jjop2.s4posebox.user.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jjop2.s4posebox.user.dto.LoginRequest;
import com.jjop2.s4posebox.user.dto.LoginResponse;
import com.jjop2.s4posebox.user.dto.SignupRequest;
import com.jjop2.s4posebox.user.service.UserService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {
	
	private final UserService userService;
	
	@PostMapping("/signup")
	public ResponseEntity<Void> signup(@Valid @RequestBody SignupRequest request) {
		
		userService.signup(request);
		
		return ResponseEntity
				.status(HttpStatus.CREATED)
				.build();
	}
	
	@PostMapping("/login")
	public ResponseEntity<LoginResponse> login(@Valid @RequestBody LoginRequest request) {
		
		LoginResponse response = userService.login(request);
		
		return ResponseEntity.ok(response);
	}

}
