package com.rider.config.jwt;

import static com.rider.constants.SecurityConstants.EXPIRATION_TIME;

import static com.rider.constants.SecurityConstants.HEADER_STRING;
import static com.rider.constants.SecurityConstants.TOKEN_PREFIX;
import static com.rider.constants.SecurityConstants.TOKEN_SECRET;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

import com.rider.entity.enums.UserRole;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class JWTTokenProvider {
	
	@Autowired
	private UserDetailsService userDetailsService;

	public String createToken(String username, UserRole role) {
		Claims claims = Jwts.claims().setSubject(username);
		claims.put("auth", AuthorityUtils.createAuthorityList(String.valueOf(role)));

		Date now = new Date();
		Date validity = new Date(now.getTime() + EXPIRATION_TIME);
		
		return Jwts.builder()
				.setClaims(claims)
				.setIssuedAt(now)
				.setExpiration(validity)
				.signWith(SignatureAlgorithm.HS256, TOKEN_SECRET)
				.compact();
	}
	
	public Authentication getAuthentication(String token) {
		UserDetails userDetails = userDetailsService.loadUserByUsername(getUsername(token));
		return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
	}
	
	public String getUsername(String token) {
		return Jwts.parser().setSigningKey(TOKEN_SECRET).parseClaimsJws(token).getBody().getSubject();
	}
	
	public String resolveToken(HttpServletRequest req) {
		String bearerToken = req.getHeader(HEADER_STRING);
		if(bearerToken != null && bearerToken.startsWith(TOKEN_PREFIX)) {
			return bearerToken.substring(7, bearerToken.length());
		}
		return null;
	}
	
	public boolean validateToken(String token) {
		try {
			Jwts.parser().setSigningKey(TOKEN_SECRET).parseClaimsJws(token);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

}
