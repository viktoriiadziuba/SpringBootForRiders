package com.rider.domain.mail;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class Mail {
	
	private String from;
	private String to;
	private String subject;
	private String content;
	
	
	public Mail() {
		this.from = "for.rider.shop@gmail.com";
	}

}
