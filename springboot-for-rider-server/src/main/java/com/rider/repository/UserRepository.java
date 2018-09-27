package com.rider.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.rider.entity.UserEntity;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {
	
	boolean existsByUsername(String username);
	
	UserEntity findByUsername(String username);
	
	boolean existsByEmail(String email);
	
	UserEntity findByEmailVerificationToken(String token);

}
