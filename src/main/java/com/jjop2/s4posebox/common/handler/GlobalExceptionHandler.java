package com.jjop2.s4posebox.common.handler;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.jjop2.s4posebox.common.code.ErrorCode;
import com.jjop2.s4posebox.common.dto.ErrorResponse;
import com.jjop2.s4posebox.common.exception.UserException;

/**
 * 프로젝트 전체 예외 처리
 */
@RestControllerAdvice
public class GlobalExceptionHandler {
	
	@ExceptionHandler(UserException.class)
	public ErrorResponse handleUserException(UserException e) {
		return new ErrorResponse(
				e.getErrorCode(),
				e.getMessage()
		);
	}
	
	@ExceptionHandler(MethodArgumentNotValidException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public ErrorResponse handleValidationException(MethodArgumentNotValidException e) {
		
		String message = e.getBindingResult()
					.getFieldError()
					.getDefaultMessage();
		
		return new ErrorResponse(
				ErrorCode.VALIDATION_ERROR,
				message
		);
	}
}
