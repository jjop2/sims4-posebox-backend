package com.jjop2.s4posebox.security.jwt;

import java.util.Date;

import javax.crypto.SecretKey;

import org.springframework.stereotype.Service;

import com.jjop2.s4posebox.user.domain.AccountType;
import com.jjop2.s4posebox.user.domain.Role;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;

@Service
public class JwtService {
	
	// 토큰 만료시간
	private static final long EXPIRATION_TIME = JwtConstants.ACCESS_TOKEN_EXPIRATION_TIME_MILLIS;
	
	// 토큰 타입
	public static final String PREFIX = JwtConstants.TOKEN_PREFIX;
	
	// 임시 서명 키
	// TODO: 서명 키 환경변수로 빼두기
	private static final SecretKey KEY = Jwts.SIG.HS256.key().build();
	
	// 권한 관련 클레임 키
	private static final String ROLE_CLAIM = JwtConstants.ROLE_CLAIM;
	
	// 계정 타입 클레임 키
	private static final String ACCOUNT_TYPE_CLAIM = JwtConstants.ACCOUNT_TYPE_CLAIM;
	
	
	public String createToken(Long userId, Role role, AccountType accountType) {
		Date nowDate = new Date();
		Date expDate = new Date( nowDate.getTime() + EXPIRATION_TIME );
		
		return Jwts.builder()
				.subject(String.valueOf(userId))
				.issuedAt(nowDate)
				.expiration(expDate)
				.signWith(KEY)
				.claim(ROLE_CLAIM, role.name())
				.claim(ACCOUNT_TYPE_CLAIM, accountType.name())
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
	
	// TODO: getRole, getAccountType이 실제로 쓰이는 곳이 있는지 확인 후,
	// 사용처 없으면 삭제 고려 (JwtFilter는 getId만 사용 중, 나머지는 CustomUserDetails에서 처리)
	public Long getId(String token) {
		return Long.valueOf(getClaims(token).getSubject());
	}
	
	public Role getRole(String token) {
		return Role.valueOf(getClaims(token).get(ROLE_CLAIM, String.class));
	}
	
	public AccountType getAccountType(String token) {
		return AccountType.valueOf(getClaims(token).get(ACCOUNT_TYPE_CLAIM, String.class));
	}
	
}
