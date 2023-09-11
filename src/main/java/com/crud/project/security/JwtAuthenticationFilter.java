package com.crud.project.security;

import java.io.IOException;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.crud.project.service.CustomerUserDetailsService;
import com.crud.project.service.JwtService;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

	private  final JwtService jwtService ;
	private final CustomerUserDetailsService customerUserDetailsService;



	public JwtAuthenticationFilter(JwtService jwtService, CustomerUserDetailsService customerUserDetailsService) {
		this.jwtService = jwtService;
		this.customerUserDetailsService = customerUserDetailsService;
	}


	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {

		String token = jwtService.getToken(request) ;
		System.out.println("Token " + token);

        if (token != null && jwtService.validateToken(token)) {
            String username = jwtService.extractUsername(token);

            UserDetails userDetails = customerUserDetailsService.loadUserByUsername(username);
            if (userDetails != null) {
            	UsernamePasswordAuthenticationToken authentication =
                    new UsernamePasswordAuthenticationToken(userDetails.getUsername() ,null , userDetails.getAuthorities());
                System.out.println("authenticated user with username :" + username);
                SecurityContextHolder.getContext().setAuthentication(authentication);

            }
        }

        filterChain.doFilter(request,response);

	}

}
