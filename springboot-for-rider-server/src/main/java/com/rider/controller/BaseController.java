package com.rider.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.rider.domain.mail.Mail;
import com.rider.service.EmailService;
import com.rider.service.UserService;


@RestController
public class BaseController {
	
	@Autowired
	private EmailService emailService;
	
	@Autowired
	private UserService userService;
	
	@GetMapping("test-message")
	public ResponseEntity<Void> sendTestMessage(@RequestParam("email") String email){
		Mail mail = Mail.builder()
				.to(email)
				.subject("Test mail subject")
				.content("Test content")
				.build();
		
		emailService.sendMessage(mail);
		
		return new ResponseEntity<Void>(HttpStatus.OK);
	}
	
	@GetMapping("verify")
	public ResponseEntity<String> verifyAccount(@RequestParam("token") String token) {
		userService.verifyAccount(token);
		
		return new ResponseEntity<String>("Your account is TRUE", HttpStatus.OK);
	}


}
