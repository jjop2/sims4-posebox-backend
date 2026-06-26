package com.jjop2.s4posebox.common.exception;

import com.jjop2.s4posebox.common.code.ErrorCode;

/**
 * 회원 관련 비즈니스 예외
 */
import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * 회원 관련 비즈니스 예외
 *
 * 회원가입, 로그인 등 회원 기능에서
 * 비즈니스 규칙을 위반했을 때 발생한다.
 *
 * ErrorCode와 메시지를 함께 전달하여
 * GlobalExceptionHandler가 적절한 응답을 생성할 수 있도록 한다.
 */
@Getter
@RequiredArgsConstructor
public class UserException extends RuntimeException {
	
	private final ErrorCode errorCode;
	
	public UserException(ErrorCode errorCode, String message) {
		super(message);
		this.errorCode = errorCode;
	}

}
