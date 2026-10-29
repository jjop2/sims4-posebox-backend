package com.jjop2.s4posebox.user.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.jjop2.s4posebox.user.domain.OAuthProvider;
import com.jjop2.s4posebox.user.domain.OauthAccount;
import com.jjop2.s4posebox.user.domain.User;

@Repository
public interface OauthAccountRepository extends JpaRepository<OauthAccount, Long> {
	
	Optional<OauthAccount> findByProviderAndProviderId(OAuthProvider provider, String providerId);
	
	boolean existsByUser(User user);
}
