package com.tsarit.form_1.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@Component
public class JwtRequestFilter extends OncePerRequestFilter {

	@Autowired
	 private  coustomUserDetailService coustomUserDetailService;
	 @Autowired
	    private  JwtUtil jwtUtil;

	    public JwtRequestFilter(coustomUserDetailService coustomUserDetailService, JwtUtil jwtUtil) {
	        this.coustomUserDetailService = coustomUserDetailService;
	        this.jwtUtil = jwtUtil;
	    }

	    @Override
	    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
	            throws ServletException, IOException {

	         String authorizationHeader = request.getHeader("Authorization");
	        String username = null;
	        String jwt = null;

	        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
	            jwt = authorizationHeader.substring(7);
	            username = jwtUtil.getUsernameFromToken(jwt);
//	            try {
//	            } catch (ExpiredJwtException e) {
//	                System.out.println("JWT Token has expired");
//	            } catch (Exception e) {
//	                System.out.println("Error extracting JWT Token");
//	            }
	        }

	        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {

	            UserDetails userDetails = this.coustomUserDetailService.loadUserByUsername(username);

	            if (jwtUtil.validateToken(jwt, userDetails)) {
	                UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
	                        new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
	                usernamePasswordAuthenticationToken
	                        .setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
	                SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
	                
	                System.out.println("User authenticated: " + username);  // Add logging here
	            } else {
	                System.out.println("Invalid JWT token for user: " + username);
	            }
	        } else {
	            System.out.println("No JWT token found or user already authenticated");
	        }
	        chain.doFilter(request, response);
	    }
}
