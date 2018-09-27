package com.rider.config.jwt;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.rider.config.jwt.JWTTokenProvider;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class JWTTokenFilterConfigurer extends SecurityConfigurerAdapter<DefaultSecurityFilterChain, HttpSecurity> {
	
	@Autowired
	private JWTTokenProvider jwtTokenProvider;

	@Override
	public void configure(HttpSecurity builder) throws Exception {
		JWTTokenFilter customFilter = new JWTTokenFilter(jwtTokenProvider);
		builder.addFilterBefore(customFilter, UsernamePasswordAuthenticationFilter.class);
	}

}
