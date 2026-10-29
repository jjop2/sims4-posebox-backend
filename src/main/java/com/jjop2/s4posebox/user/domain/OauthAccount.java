package com.jjop2.s4posebox.user.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Getter
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(
	name = "oauth_accounts",
	uniqueConstraints = {
		@UniqueConstraint(
			columnNames = {"provider", "provider_id"}
		)
	}
)
public class OauthAccount {
	
	@Id
	@Column(name = "user_id", nullable = false)
	private Long userId;
	
	@OneToOne(fetch = FetchType.LAZY)
	@MapsId
	@JoinColumn(name = "user_id", nullable = false)
	private User user;
	
	@Enumerated(EnumType.STRING)
	@Column(nullable = false, length = 20)
	private OAuthProvider provider;
	
	@Column(name = "provider_id", nullable = false, length = 255)
	private String providerId;
	
	@Column(nullable = false, length = 255)
	private String email;
	
	@Builder
	public OauthAccount(
		User user,
		OAuthProvider provider,
		String providerId,
		String email
	) {
		this.user = user;
		this.provider = provider;
		this.providerId = providerId;
		this.email = email;
	}
}
