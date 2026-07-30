package com.jjop2.s4posebox.security.jwt;

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
	// TODO: 필요한 경우 JWT 관련 상수만 모아둔 클래스를 만들기
	public static final String PREFIX = "Bearer ";
	
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
	
	
	private Claims getClaims(String token) {
		
		return Jwts.parser()
				.verifyWith(KEY)
				.build()
				.parseSignedClaims(token)
				.getPayload();
	}
	
	public boolean validateToken(String token) {

	    try {
	        getClaims(token);
	        return true;
	    } catch (Exception e) {
	        return false;
	    }

	}
	
	// TODO: getClaims를 한번만 실행하도록 리팩토링 후보
	public String getLoginId(String token) {
		return getClaims(token).getSubject();
	}
	
	public String getRole(String token) {
		return getClaims(token).get(ROLE_CLAIM, String.class);
	}
	
}
