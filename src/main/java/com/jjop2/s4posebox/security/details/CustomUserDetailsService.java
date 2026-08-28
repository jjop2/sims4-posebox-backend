package com.jjop2.s4posebox.security.details;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.jjop2.s4posebox.common.code.ErrorCode;
import com.jjop2.s4posebox.common.exception.UserException;
import com.jjop2.s4posebox.user.domain.LocalAccount;
import com.jjop2.s4posebox.user.domain.User;
import com.jjop2.s4posebox.user.repository.LocalAccountRepository;
import com.jjop2.s4posebox.user.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {
	
	private final LocalAccountRepository localAccountRepository;
	
	private final UserRepository userRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		LocalAccount principal = localAccountRepository.findByEmail(username)
						.orElseThrow(() -> new UsernameNotFoundException(username));
		
		return new CustomUserDetails(principal.getUser(), principal.getPassword());
	}
	
	public UserDetails loadUserById(Long id) {
		
		User principal = userRepository.findById(id)
			.orElseThrow(() -> new UserException(ErrorCode.USER_NOT_FOUND, "User not found."));
		
		return new CustomUserDetails(principal);
	}
	
	
}
