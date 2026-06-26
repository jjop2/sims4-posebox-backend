package com.jjop2.s4posebox.jwt;

import java.util.Date;

import javax.crypto.SecretKey;

import org.springframework.stereotype.Service;

import com.jjop2.s4posebox.user.domain.Role;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;

@Service
public class JwtService {
	
	// 토큰 만료시간
	// TODO: 만료시간(현재 24시간) 변경
	private static final long EXPIRATION_TIME = 24 * 60 * 60 * 1000L;
	
	// 토큰 타입
	private static final String PREFIX = "Bearer ";
	
	// 임시 서명 키
	// TODO: 서명 키 환경변수로 빼두기
	private static final SecretKey KEY = Jwts.SIG.HS256.key().build();
	
	// 권한 관련 클레임 키
	private static final String ROLE_CLAIM = "role";
	
	
	public String createToken(String loginId, Role role) {
		Date nowDate = new Date();
		Date expDate = new Date( nowDate.getTime() + EXPIRATION_TIME );
		
		return Jwts.builder()
				.subject(loginId)
				.issuedAt(nowDate)
				.expiration(expDate)
				.signWith(KEY)
				.claim(ROLE_CLAIM, role.name())
				.compact();
	}
	
	
	// TODO: 시큐리티 적용할 때 리팩토링
	private Claims getClaims(String token) {
		
		return Jwts.parser()
				.verifyWith(KEY)
				.build()
				.parseSignedClaims(token)
				.getPayload();
	}
	
	public String getLoginId(String token) { return getClaims(token).getSubject(); }
	public String getRole(String token) { return getClaims(token).get(ROLE_CLAIM, String.class); }
	
	public boolean validateToken(String token) {

	    try {
	        getClaims(token);
	        return true;
	    } catch (Exception e) {
	        return false;
	    }

	}
	
}
