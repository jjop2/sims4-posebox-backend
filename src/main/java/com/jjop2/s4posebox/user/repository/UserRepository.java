package com.jjop2.s4posebox.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.jjop2.s4posebox.user.domain.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

}
