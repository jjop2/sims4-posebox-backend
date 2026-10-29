package com.jjop2.s4posebox.auth.controller;

import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jjop2.s4posebox.auth.AuthConstants;
import com.jjop2.s4posebox.auth.dto.LoginRequest;
import com.jjop2.s4posebox.auth.dto.LoginResponse;
import com.jjop2.s4posebox.auth.dto.LoginResult;
import com.jjop2.s4posebox.auth.dto.RefreshResponse;
import com.jjop2.s4posebox.auth.dto.RefreshResult;
import com.jjop2.s4posebox.auth.service.AuthService;
import com.jjop2.s4posebox.common.ApiPaths;
import com.jjop2.s4posebox.common.code.ErrorCode;
import com.jjop2.s4posebox.common.exception.UserException;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping(ApiPaths.AUTH)
public class AuthController {

	private final AuthService authService;
	
	@PostMapping(ApiPaths.AUTH_LOGIN)
	public ResponseEntity<LoginResponse> login(@Valid @RequestBody LoginRequest request) {
		
		LoginResult result = authService.login(request);
		
		ResponseCookie cookie = ResponseCookie.from(AuthConstants.REFRESH_TOKEN_COOKIE_NAME, result.refreshToken())
			.httpOnly(true)
			.secure(false)
			.path("/")
			.maxAge(AuthConstants.REFRESH_TOKEN_EXPIRATION_TIME_SECONDS)
			.sameSite("Lax")
			.build();
			
		return ResponseEntity.ok()
			.header(HttpHeaders.SET_COOKIE, cookie.toString())
			.body(result.response());
	}
	
	@PostMapping(ApiPaths.AUTH_REFRESH)
	public ResponseEntity<RefreshResponse> refresh(@CookieValue(
		value = AuthConstants.REFRESH_TOKEN_COOKIE_NAME,
		required = false
	) String refreshTokenValue) {
		
		if (refreshTokenValue == null ) {
			throw new UserException(
				ErrorCode.REFRESH_TOKEN_NOT_FOUND,
				"Refresh token cookie is missing."
			);
		}
		
		RefreshResult result = authService.refresh(refreshTokenValue);
		
		ResponseCookie cookie = ResponseCookie.from(AuthConstants.REFRESH_TOKEN_COOKIE_NAME, result.refreshToken())
			.httpOnly(true)
			.secure(false)
			.path("/")
			.maxAge(AuthConstants.REFRESH_TOKEN_EXPIRATION_TIME_SECONDS)
			.sameSite("Lax")
			.build();		
		
		return ResponseEntity.ok()
			.header(HttpHeaders.SET_COOKIE, cookie.toString())
			.body(result.response());
	}
	
	@PostMapping(ApiPaths.AUTH_LOGOUT)
	public ResponseEntity<Void> logout(@CookieValue(
		value = AuthConstants.REFRESH_TOKEN_COOKIE_NAME,
		required = false
	) String refreshTokenValue) {
		if (refreshTokenValue != null) {
			authService.logout(refreshTokenValue);
		}
		
		ResponseCookie expiredCookie = ResponseCookie.from(AuthConstants.REFRESH_TOKEN_COOKIE_NAME, "")
			.httpOnly(true)
			.secure(false)
			.path("/")
			.maxAge(0)
			.sameSite("Lax")
			.build();
		
		return ResponseEntity.noContent()
			.header(HttpHeaders.SET_COOKIE, expiredCookie.toString())
			.build();
	}

}
