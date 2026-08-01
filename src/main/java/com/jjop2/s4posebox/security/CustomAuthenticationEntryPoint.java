package com.jjop2.s4posebox.security;

import java.io.IOException;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import com.jjop2.s4posebox.common.code.ErrorCode;
import com.jjop2.s4posebox.common.dto.ErrorResponse;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import tools.jackson.databind.json.JsonMapper;

@Component
@RequiredArgsConstructor
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {
	
	private final JsonMapper jsonMapper;

	@Override
	public void commence(
		HttpServletRequest request,
		HttpServletResponse response,
		AuthenticationException authException
	) throws IOException, ServletException {
		
		ErrorCode errorCode = ErrorCode.AUTHENTICATION_REQUIRED;
		
		response.setStatus(errorCode.getStatus().value());
		response.setContentType("application/json; charset=utf-8");
		
		ErrorResponse errorResponse =
			new ErrorResponse(errorCode, "Authentication is required.");
			
		response.getWriter().write(
			jsonMapper.writeValueAsString(errorResponse)
		);
	}

}
