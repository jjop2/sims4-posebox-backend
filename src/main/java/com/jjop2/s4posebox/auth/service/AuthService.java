package com.jjop2.s4posebox.auth.service;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jjop2.s4posebox.auth.domain.RefreshToken;
import com.jjop2.s4posebox.auth.dto.LoginRequest;
import com.jjop2.s4posebox.auth.dto.LoginResponse;
import com.jjop2.s4posebox.auth.dto.LoginResult;
import com.jjop2.s4posebox.auth.dto.RefreshResponse;
import com.jjop2.s4posebox.auth.dto.RefreshResult;
import com.jjop2.s4posebox.auth.dto.UserDto;
import com.jjop2.s4posebox.common.code.ErrorCode;
import com.jjop2.s4posebox.common.exception.UserException;
import com.jjop2.s4posebox.security.details.CustomUserDetails;
import com.jjop2.s4posebox.security.jwt.JwtService;
import com.jjop2.s4posebox.user.domain.AccountType;
import com.jjop2.s4posebox.user.domain.Role;
import com.jjop2.s4posebox.user.domain.User;
import com.jjop2.s4posebox.user.repository.UserRepository;
import com.jjop2.s4posebox.user.service.UserService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthService {
	
	private final UserRepository userRepository;
	private final UserService userService;
	private final JwtService jwtService;
	private final RefreshTokenService refreshTokenService;
	private final AuthenticationManager authenticationManager;
	
	/*
	 * 로그인
	 * 
	 * - 사용자 인증
	 * - Access, Refresh Token 발급
	 * - 로그인 응답 반환
	 */
	@Transactional
	public LoginResult login(LoginRequest request) {
		
		Authentication authentication;
		
		String normalizedEmail = request.getEmail().trim().toLowerCase();
		
		try {
			authentication = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(
					normalizedEmail,
					request.getPassword()
				)
			);
		} catch (BadCredentialsException e) {
			throw new UserException(
				ErrorCode.INVALID_CREDENTIALS,
				"Invalid login ID or password."
			);
		}
		
		CustomUserDetails principal =
			(CustomUserDetails) authentication.getPrincipal();
		
		User user = userRepository.findById(principal.getId())
			.orElseThrow(() -> new UserException(ErrorCode.USER_NOT_FOUND, "User not found."));
		
		String nickname = null;
		
		if (user.getRole() == Role.CREATOR) {
			// TODO: creator에서 nickname 추출
		}
		
		UserDto userDto = new UserDto(
			user.getId(),
			user.getRole(),
			AccountType.LOCAL,
			nickname
		);
		
		String accessToken = jwtService.createToken(
			user.getId(),
			user.getRole(),
			AccountType.LOCAL
		);
		
		RefreshToken refreshToken = refreshTokenService.issue(user);
		
		user.updateLastLoginAt();
		
		return new LoginResult(new LoginResponse(accessToken, userDto), refreshToken.getToken());
	};
	
	@Transactional
	public RefreshResult refresh(String oldTokenValue) {
		RefreshToken refreshToken = refreshTokenService.rotate(oldTokenValue);
		
		User user = refreshToken.getUser();
		
		AccountType accountType = userService.resolveAccountType(user);
		
		String nickname = null;
		
		if (user.getRole() == Role.CREATOR) {
			// TODO: creator에서 nickname 추출
		}
		
		UserDto userDto = new UserDto(
			user.getId(),
			user.getRole(),
			AccountType.LOCAL,
			nickname
		);
		
		
		String accessToken = jwtService.createToken(
			user.getId(),
			user.getRole(),
			accountType
		);
		
		return new RefreshResult(new RefreshResponse(accessToken, userDto), refreshToken.getToken());		
	};
	
	@Transactional
	public void logout(String tokenValue) {
		refreshTokenService.revoke(tokenValue);
	}

}
