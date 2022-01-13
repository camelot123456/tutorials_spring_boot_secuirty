package com.springtutorial.jwt;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.filter.OncePerRequestFilter;

import com.google.common.base.Strings;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

public class JwtTokenVerifier extends OncePerRequestFilter{

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		String authorizationHeader = request.getHeader("Authorization");
		
		if (Strings.isNullOrEmpty(authorizationHeader) || authorizationHeader.startsWith("Bearer ")) {
			filterChain.doFilter(request, response);
			return;
		}
		
		try {
			String token = authorizationHeader.replace("Bearer ", "");
			String secretKey = "securesecuresecuresecuresecuresecuresecuresecuresecuresecuresecuresecuresecure";
			Jws<Claims> claimsJws = Jwts.parser()
				.setSigningKey(Keys.hmacShaKeyFor(secretKey.getBytes()))
				.parseClaimsJws(token);
			
			Claims body = claimsJws.getBody();
			String username = body.getSubject();
//			body.get("authorization, "Bearer", )
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	
	
}
