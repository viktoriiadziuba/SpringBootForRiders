package com.rider.domain;

import com.rider.entity.enums.UserRole;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UserDTO {
	
	private Long id;
	
	private String username;
	
	private String email;
	
	private String password;
	
	private String firstName;
	
	private String lastName;
	
	private String phoneNumber;
	
	private UserRole role;
	
	

}
