package com.jjop2.s4posebox.security.filter;

import java.io.IOException;

import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.jjop2.s4posebox.security.details.CustomUserDetails;
import com.jjop2.s4posebox.security.details.CustomUserDetailsService;
import com.jjop2.s4posebox.security.jwt.JwtService;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {
	
	private final JwtService jwtService;
	private final CustomUserDetailsService customUserDetailsService;

	@Override
	protected void doFilterInternal(
		HttpServletRequest request,
		HttpServletResponse response,
		FilterChain filterChain
	) throws ServletException, IOException {
		
		String authorization = request.getHeader(HttpHeaders.AUTHORIZATION);
		
		// JWT가 없는 요청(로그인, 회원가입, 공개 API 등)은 인증을 시도하지 않고 다음 필터로 전달
		if (authorization == null || !authorization.startsWith("Bearer ")) {
			filterChain.doFilter(request, response);
			return;
		}
		
		// TODO: PREFIX 상수 모음으로 옮길 때 수정
		String token = authorization.substring(JwtService.PREFIX.length());
		
		if (!jwtService.validateToken(token)) {
			filterChain.doFilter(request, response);
			return;
		}
		
		String loginId = jwtService.getLoginId(token);
		
		CustomUserDetails principal =
			(CustomUserDetails) customUserDetailsService.loadUserByUsername(loginId);
		
		Authentication authentication =
			new UsernamePasswordAuthenticationToken(principal, null, principal.getAuthorities());
		
		SecurityContextHolder.getContext().setAuthentication(authentication);
		
		filterChain.doFilter(request, response);
	}

}
