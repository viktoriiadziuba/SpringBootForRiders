package com.rider;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.rider.entity.UserEntity;
import com.rider.entity.enums.UserRole;
import com.rider.repository.UserRepository;

@SpringBootApplication
@EnableJpaRepositories(basePackages = "com.rider.repository")
public class SpringBootForRiderServerApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(SpringBootForRiderServerApplication.class, args);
	}
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private PasswordEncoder passwordEncoder;

	@Override
	public void run(String... args) throws Exception {
		if(userRepository.count() == 0) {
			UserEntity userEntity = new UserEntity();
			userEntity.setFirstName("Admin");
			userEntity.setLastName("Admin");
			userEntity.setUsername("admin");
			userEntity.setPhoneNumber("0968404150");
			userEntity.setEmail("for.rider.shop@gmail.com");
			userEntity.setRole(UserRole.ROLE_ADMIN);
			userEntity.setPassword(passwordEncoder.encode("admin"));
			
			userRepository.save(userEntity);
		}
		
	}
}
