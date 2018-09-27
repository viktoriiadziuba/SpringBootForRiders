package com.rider.service;

import java.util.List;

import com.rider.domain.UserDTO;

public interface UserService {
	
	void save(UserDTO dto);
	
	UserDTO findById(Long id);
	
	List<UserDTO> findAll();
	
	boolean existsByUsername(String username);
	
	UserDTO findByUsername(String username);

	boolean existsByEmail(String email);
	
	String signin(String username, String password);
	
	void verifyAccount(String verifyToken);

}
