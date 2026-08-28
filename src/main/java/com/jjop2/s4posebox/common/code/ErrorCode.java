package com.jjop2.s4posebox.common.code;

import org.springframework.http.HttpStatus;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * 프로젝트 전반에서 사용하는 공통 에러 코드
 */
@Getter
@RequiredArgsConstructor
public enum ErrorCode {
	
	// Validation
	VALIDATION_ERROR(HttpStatus.BAD_REQUEST),

	// User
    USER_DUPLICATE_EMAIL(HttpStatus.CONFLICT),
    USER_NOT_FOUND(HttpStatus.NOT_FOUND),
	
    // Authentication
    INVALID_CREDENTIALS(HttpStatus.UNAUTHORIZED),
    AUTHENTICATION_REQUIRED(HttpStatus.UNAUTHORIZED),
    INVALID_TOKEN(HttpStatus.UNAUTHORIZED),
    
    // Authorization
    ACCESS_DENIED(HttpStatus.FORBIDDEN);
    
    private final HttpStatus status;
}
