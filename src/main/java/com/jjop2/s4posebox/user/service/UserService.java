package com.jjop2.s4posebox.user.service;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.jjop2.s4posebox.common.code.ErrorCode;
import com.jjop2.s4posebox.common.exception.UserException;
import com.jjop2.s4posebox.jwt.JwtService;
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
	 * TODO : 아이디/비밀번호 불일치 하나로 합치기
	 */
	public LoginResponse /* 임시 */ login(LoginRequest request) {	
		User user = userRepository.findByLoginId(request.getLoginId())
			.orElseThrow(() -> new UserException(
				ErrorCode.USER_NOT_FOUND,
				"User not found."
			));
		
		if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
			throw new UserException(
				ErrorCode.USER_INVALID_PASSWORD,
				"Invaild password."
			);
		}
		
		String jwt = jwtService.createToken(user.getLoginId(), user.getRole());
		
		// TODO: 로그인 성공 처리 수정
		return new LoginResponse(jwt);
	};
	
}
