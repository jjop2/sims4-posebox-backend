package com.jjop2.s4posebox.user.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.jjop2.s4posebox.user.domain.LocalAccount;

@Repository
public interface LocalAccountRepository extends JpaRepository<LocalAccount, Long> {
	
	boolean existsByEmail(String email);
	
	Optional<LocalAccount> findByEmail(String email);

}
