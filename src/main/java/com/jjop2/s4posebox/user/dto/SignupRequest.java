package com.jjop2.s4posebox.user.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 회원가입 요청용 DTO
 *
 * 클라이언트가 전달하는 회원가입 정보를 담는다.
 * 이메일, 비밀번호로 구성되며,
 * 유효성 검사를 수행한다.
 *
 * 엔티티와 분리하여 필요한 데이터만 전달받고,
 * 내부 DB 구조가 외부에 노출되는 것을 방지한다.
 */
@Getter
@NoArgsConstructor
public class SignupRequest {
	
	@NotBlank(message = "Email is required.")
	@Email(message = "Invalid email format.")
	@Size(
		max = 255,
		message = "Email must not exceed 255 characters."
		)
	private String email;
	
    @NotBlank(message = "Password is required.")
    @Size(
        min = 8,
        max = 50,
        message = "Password must be between 8 and 50 characters."
    )
    @Pattern(
        regexp = "^(?=.*[a-zA-Z])(?=.*[0-9])(?=.*[!@#$%^&*()_+\\-=\\[\\]{};':\"\\\\|,.<>/?])[a-zA-Z0-9!@#$%^&*()_+\\-=\\[\\]{};':\"\\\\|,.<>/?]*$",
        message = "Password must include letters, numbers, and special characters."
    )
    private String password;
	
}
