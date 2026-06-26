package com.jjop2.s4posebox.common.code;

import lombok.Getter;

/**
 * 프로젝트 전반에서 사용하는 공통 에러 코드
 */
@Getter
public enum ErrorCode {
	
	VALIDATION_ERROR,

    USER_DUPLICATE_LOGIN_ID,

    USER_DUPLICATE_EMAIL,

    USER_NOT_FOUND,
    
    USER_INVALID_PASSWORD,
	
}
