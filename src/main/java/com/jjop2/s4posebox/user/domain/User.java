package com.jjop2.s4posebox.user.domain;

import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 회원 엔티티
 *
 * Builder 패턴을 사용하여 객체 생성 시
 * 필드의 의미를 명확하게 표현하고 읽기 쉬워졌다.
 * 생성자 파라미터 순서 실수를 방지할 수 있다.
 */
@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "user")
public class User {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(nullable = false, unique = true, length = 30)
	private String loginId;
	
	@Column(nullable = false, length = 100)
	private String password;
	
	@Column(nullable = false, unique = true, length = 100)
	private String email;
	
	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private Role role;
	
	@CreationTimestamp
	@Column(nullable = false, updatable = false)
	private LocalDateTime createdAt;

	@Builder
	public User(
			String loginId,
			String password,
			String email,
			Role role
	) {
		this.loginId = loginId;
		this.password = password;
		this.email = email;
		this.role = role;
	}
	
}