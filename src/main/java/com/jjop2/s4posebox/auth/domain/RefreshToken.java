package com.jjop2.s4posebox.auth.domain;

import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;

import com.jjop2.s4posebox.user.domain.User;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Getter
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "refresh_tokens")
public class RefreshToken {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@ToString.Exclude
	@Column(nullable = false, unique = true, length = 255)
	private String token;
	
	@Column(name = "expires_at", nullable = false)
	private LocalDateTime expiresAt;
	
	@CreationTimestamp
	@Column(name = "created_at", nullable = false, updatable = false)
	private LocalDateTime createdAt;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id", nullable = false)
	private User user;
	
	@Builder
	public RefreshToken(
		String token,
		LocalDateTime expiresAt,
		User user
	) {
		this.token = token;
		this.expiresAt = expiresAt;
		this.user = user;
	}
	
	public void rotate(String newToken, LocalDateTime newExpiresAt) {
	    this.token = newToken;
	    this.expiresAt = newExpiresAt;
	}

}
