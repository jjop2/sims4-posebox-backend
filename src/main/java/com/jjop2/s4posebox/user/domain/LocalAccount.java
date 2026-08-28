package com.jjop2.s4posebox.user.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.MapsId;
import jakarta.persistence.OneToOne;
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
@Table(name = "local_accounts")
public class LocalAccount {
	
	@Id
	@Column(name = "user_id", nullable = false)
	private Long userId;
	
	@OneToOne(fetch = FetchType.LAZY)
	@MapsId
	@JoinColumn(name = "user_id", nullable = false)
	private User user;
	
	@Column(nullable = false, unique = true, length = 255)
	private String email;
	
	@ToString.Exclude
	@Column(nullable = false, length = 255)
	private String password;

	@Builder
	public LocalAccount(
		User user,
		String email,
		String password
	) {
		this.user = user;
		this.email = email;
		this.password = password;
	}
}
