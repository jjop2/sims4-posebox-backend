package com.jjop2.s4posebox.security.details;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.jjop2.s4posebox.user.domain.User;
import com.jjop2.s4posebox.user.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {
	
	private final UserRepository userRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		User principal = userRepository.findByLoginId(username)
								.orElseThrow(() -> new UsernameNotFoundException(
										// TODO: 배포 시 username 삭제
										"User not found. loginId=" + username
								));
		
		return new CustomUserDetails(principal);
	}
	
	
}
