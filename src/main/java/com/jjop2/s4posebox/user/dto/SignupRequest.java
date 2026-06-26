package com.jjop2.s4posebox.user.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 회원가입 요청용 DTO
 *
 * 클라이언트가 전달하는 회원가입 정보를 담는다.
 * 로그인 ID, 비밀번호, 이메일로 구성되며,
 * 유효성 검사를 수행한다.
 *
 * 엔티티와 분리하여 필요한 데이터만 전달받고,
 * 내부 DB 구조가 외부에 노출되는 것을 방지한다.
 */
@Getter
@NoArgsConstructor
public class SignupRequest {
	
	@NotBlank(message = "Login ID is required.")
    @Size(
        min = 4,
        max = 30,
        message = "Login ID must be between 4 and 30 characters."
    )
    private String loginId;

    @NotBlank(message = "Password is required.")
    @Size(
        min = 8,
        max = 50,
        message = "Password must be between 8 and 50 characters."
    )
    private String password;

    @NotBlank(message = "Email is required.")
    @Email(message = "Invalid email format.")
    @Size(
        max = 100,
        message = "Email must not exceed 100 characters."
    )
    private String email;
	
}
