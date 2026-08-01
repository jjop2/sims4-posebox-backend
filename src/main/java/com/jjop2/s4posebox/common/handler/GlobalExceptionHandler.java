package com.jjop2.s4posebox.common.handler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.jjop2.s4posebox.common.code.ErrorCode;
import com.jjop2.s4posebox.common.dto.ErrorResponse;
import com.jjop2.s4posebox.common.exception.UserException;

/**
 * Controller 이후 예외 처리
 */
@RestControllerAdvice
public class GlobalExceptionHandler {
	
	@ExceptionHandler(UserException.class)
	public ResponseEntity<ErrorResponse> handleUserException(UserException e) {
		
		ErrorCode errorCode = e.getErrorCode();
		
		return ResponseEntity
				.status(errorCode.getStatus())
				.body(new ErrorResponse(errorCode, e.getMessage()));
	}
	
	@ExceptionHandler(MethodArgumentNotValidException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public ResponseEntity<ErrorResponse> handleValidationException(MethodArgumentNotValidException e) {
		
		String message = e.getBindingResult()
					.getFieldError()
					.getDefaultMessage();
		
		ErrorCode errorCode = ErrorCode.VALIDATION_ERROR;
		
		return ResponseEntity
				.status(errorCode.getStatus())
				.body(new ErrorResponse(errorCode, message));
	}
}
