package com.jjop2.s4posebox.user.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jjop2.s4posebox.common.code.ErrorCode;
import com.jjop2.s4posebox.common.exception.UserException;
import com.jjop2.s4posebox.user.domain.AccountType;
import com.jjop2.s4posebox.user.domain.LocalAccount;
import com.jjop2.s4posebox.user.domain.Role;
import com.jjop2.s4posebox.user.domain.User;
import com.jjop2.s4posebox.user.dto.SignupRequest;
import com.jjop2.s4posebox.user.repository.LocalAccountRepository;
import com.jjop2.s4posebox.user.repository.OauthAccountRepository;
import com.jjop2.s4posebox.user.repository.UserRepository;

import lombok.RequiredArgsConstructor;

/**
 * 회원 관련 비즈니스 로직 처리
 */
@Service
@RequiredArgsConstructor
public class UserService {
	
	private final UserRepository userRepository;
	private final LocalAccountRepository localAccountRepository;
	private final OauthAccountRepository oauthAccountRepository;
	private final PasswordEncoder passwordEncoder;
	
	/**
	 * 회원가입
	 * - 이메일 중복 검사
	 * - 회원 저장
	 */
	@Transactional
	public void signup(SignupRequest request) {
		
		String normalizedEmail = request.getEmail().trim().toLowerCase();
		
		if (localAccountRepository.existsByEmail(normalizedEmail)) {
			throw new UserException(
				ErrorCode.USER_DUPLICATE_EMAIL,
				"Email already exists."
			);
		}
		
		String encodedPassword = passwordEncoder.encode(request.getPassword());
		
		User user = User.builder()
			.role(Role.USER)
			.build();
		
		userRepository.save(user);
		
		LocalAccount localAccount = LocalAccount.builder()
					.user(user)
					.email(normalizedEmail)
					.password(encodedPassword)
					.build();
		
		localAccountRepository.save(localAccount);
	}
	
	public AccountType resolveAccountType(User user) {
		if (localAccountRepository.existsById(user.getId())) {
	        return AccountType.LOCAL;
	    }
	    if (oauthAccountRepository.existsById(user.getId())) {
	        return AccountType.OAUTH;
	    }
	    throw new UserException(
	    	ErrorCode.USER_NOT_FOUND,
	    	"Account type could not be resolved."
	    );
	}
}
