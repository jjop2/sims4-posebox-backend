package com.jjop2.s4posebox.user.service;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.jjop2.s4posebox.common.code.ErrorCode;
import com.jjop2.s4posebox.common.exception.UserException;
import com.jjop2.s4posebox.security.details.CustomUserDetails;
import com.jjop2.s4posebox.security.jwt.JwtService;
import com.jjop2.s4posebox.user.domain.Role;
import com.jjop2.s4posebox.user.domain.User;
import com.jjop2.s4posebox.user.dto.LoginRequest;
import com.jjop2.s4posebox.user.dto.LoginResponse;
import com.jjop2.s4posebox.user.dto.SignupRequest;
import com.jjop2.s4posebox.user.repository.UserRepository;

import lombok.RequiredArgsConstructor;

/**
 * 회원 관련 비즈니스 로직 처리
 */
@Service
@RequiredArgsConstructor
public class UserService {
	
	private final UserRepository userRepository;
	private final BCryptPasswordEncoder passwordEncoder;
	private final JwtService jwtService;
	private final AuthenticationManager authenticationManager;
	
	/**
	 * 회원가입
	 * - 로그인 ID 중복 검사
	 * - 이메일 중복 검사
	 * - 회원 저장
	 */
	public User signup(SignupRequest request) {
		
		if (userRepository.existsByLoginId(request.getLoginId())) {
			throw new UserException(
				ErrorCode.USER_DUPLICATE_LOGIN_ID,
				"Login ID already exists."
			);
		}
		
		if (userRepository.existsByEmail(request.getEmail())) {
			throw new UserException(
				ErrorCode.USER_DUPLICATE_EMAIL,
				"Email already exists."
			);
		}
		
		String encodedPassword = passwordEncoder.encode(request.getPassword());
		
		User user = User.builder()
			.loginId(request.getLoginId())
			.password(encodedPassword)
			.email(request.getEmail())
			.role(Role.USER)
			.build();
		
		return userRepository.save(user);
		
	}
	
	/*
	 * 로그인
	 * 
	 * - 사용자 인증
	 * - JWT Access Token 발급
	 * - 로그인 응답 반환
	 * 
	 * - TODO: 로그인 실패 응답을 프로젝트 공통 형식으로 변경
	 */
	public LoginResponse login(LoginRequest request) {	
		
		Authentication authentication =
			authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(
					request.getLoginId(),
					request.getPassword()
				)
			);
		
		CustomUserDetails principal =
			(CustomUserDetails) authentication.getPrincipal();
		
		String jwt = jwtService.createToken(
			principal.getUsername(),
			principal.getRole()
		);
		
		// TODO: Refresh Token 발급 및 저장
		return new LoginResponse(jwt);
	};
	
}
