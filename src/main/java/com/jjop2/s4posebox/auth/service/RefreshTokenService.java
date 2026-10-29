package com.jjop2.s4posebox.auth.service;

import java.time.LocalDateTime;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jjop2.s4posebox.auth.AuthConstants;
import com.jjop2.s4posebox.auth.domain.RefreshToken;
import com.jjop2.s4posebox.auth.repository.RefreshTokenRepository;
import com.jjop2.s4posebox.common.code.ErrorCode;
import com.jjop2.s4posebox.common.exception.UserException;
import com.jjop2.s4posebox.common.util.RandomTokenGenerator;
import com.jjop2.s4posebox.user.domain.User;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RefreshTokenService {
	
	private final RefreshTokenRepository refreshTokenRepository;
	private final RandomTokenGenerator randomTokenGenerator;
	
	@Transactional
	public RefreshToken issue(User user) {
		String tokenValue = randomTokenGenerator.generate();
		LocalDateTime expiresAt = LocalDateTime.now().plusSeconds(AuthConstants.REFRESH_TOKEN_EXPIRATION_TIME_SECONDS);
		
		RefreshToken refreshToken = RefreshToken.builder()
			.token(tokenValue)
			.expiresAt(expiresAt)
			.user(user)
			.build();
		
		return refreshTokenRepository.save(refreshToken);
	}
	
	@Transactional
	public RefreshToken rotate(String oldTokenValue) {
		RefreshToken refreshToken = refreshTokenRepository.findByToken(oldTokenValue)
			.orElseThrow(() -> new UserException(
				ErrorCode.REFRESH_TOKEN_NOT_FOUND,
				"Refresh token not found. Please log in again."
			));
		
		if (refreshToken.getExpiresAt().isBefore(LocalDateTime.now())) {
			throw new UserException(
				ErrorCode.REFRESH_TOKEN_EXPIRED,
				"Refresh token has expired. Please log in again."
			);
		}
		
		String newTokenValue = randomTokenGenerator.generate();
		LocalDateTime newExpiresAt = LocalDateTime.now().plusSeconds(AuthConstants.REFRESH_TOKEN_EXPIRATION_TIME_SECONDS);
		
		refreshToken.rotate(newTokenValue, newExpiresAt);
		
		return refreshToken;
	}
	
	@Transactional
	public void revoke(String tokenValue) {
		refreshTokenRepository.findByToken(tokenValue)
			.ifPresent(refreshToken -> refreshTokenRepository.delete(refreshToken));
	}

}
