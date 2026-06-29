package com.jjop2.s4posebox.security.details;

import java.util.Collection;
import java.util.List;

import org.jspecify.annotations.Nullable;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.jjop2.s4posebox.user.domain.Role;
import com.jjop2.s4posebox.user.domain.User;

import lombok.Getter;

@Getter
public class CustomUserDetails implements UserDetails {
	
	private User user;
		
	public CustomUserDetails(User user) {
		this.user = user;
	}
	

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return List.of(
			new SimpleGrantedAuthority("ROLE_" + user.getRole().name())
		);
	}

	@Override
	public @Nullable String getPassword() {
		return user.getPassword();
	}
	
	@Override
	public String getUsername() {
		return user.getLoginId();
	}
	
	public Role getRole() {
		return user.getRole();
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
