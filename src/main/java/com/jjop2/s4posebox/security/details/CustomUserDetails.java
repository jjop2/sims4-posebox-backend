package com.jjop2.s4posebox.security.details;

import java.util.Collection;
import java.util.List;

import org.jspecify.annotations.Nullable;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.jjop2.s4posebox.user.domain.LocalAccount;
import com.jjop2.s4posebox.user.domain.Role;
import com.jjop2.s4posebox.user.domain.User;

import lombok.Getter;
import lombok.ToString;

@ToString
public class CustomUserDetails implements UserDetails {
	
	private User user;
	private String password;
	
	// 로그인 검증용 (LocalAccount 필요)
	public CustomUserDetails(User user, String password) {
		this.user = user;
		this.password = password;
	}

	// JwtFilter용
	public CustomUserDetails(User user) {
		this.user = user;
		this.password = null;
	}
	
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return List.of(
			new SimpleGrantedAuthority("ROLE_" + user.getRole().name())
		);
	}

	@Override
	public @Nullable String getPassword() {
		return password;
	}
	
	@Override
	public String getUsername() {
		return String.valueOf(user.getId());
	}
	
	public Role getRole() {
		return user.getRole();
	}
	
	public Long getId() {
		return user.getId();
	}

	@Override
	public boolean isAccountNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}


	@Override
	public boolean isAccountNonLocked() {
		// TODO Auto-generated method stub
		return true;
	}


	@Override
	public boolean isCredentialsNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}


	@Override
	public boolean isEnabled() {
		// TODO Auto-generated method stub
		return true;
	}
	
	
}
