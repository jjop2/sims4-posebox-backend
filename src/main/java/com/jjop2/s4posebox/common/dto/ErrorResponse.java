package com.jjop2.s4posebox.common.dto;

import com.jjop2.s4posebox.common.code.ErrorCode;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ErrorResponse {
	
	private ErrorCode errorCode;
	
	private String message;

}
