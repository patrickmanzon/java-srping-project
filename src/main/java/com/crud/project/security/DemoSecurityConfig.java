package com.crud.project.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.crud.project.service.CustomerUserDetailsService;

@Configuration
public class DemoSecurityConfig {

	private final JwtAuthenticationFilter jwtAuthenticationFilter;

	public DemoSecurityConfig(JwtAuthenticationFilter jwtAuthenticationFilter) {
		this.jwtAuthenticationFilter = jwtAuthenticationFilter;
	}

    //beans
    //bcrypt bean definition
    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    //authenticationProvider bean definition
    @Bean
    public DaoAuthenticationProvider authenticationProvider(CustomerUserDetailsService userService) {
        DaoAuthenticationProvider auth = new DaoAuthenticationProvider();
        auth.setUserDetailsService(userService); //set the custom user details service
        auth.setPasswordEncoder(passwordEncoder()); //set the password encoder - bcrypt
        return auth;
    }
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http.authorizeHttpRequests(configurer ->
                    configurer
                    		.requestMatchers(HttpMethod.POST, "/api/login").permitAll()
                    		.requestMatchers(HttpMethod.GET, "/api/user").hasRole("EMPLOYEE")
                            .requestMatchers(HttpMethod.GET, "/api/courses").hasRole("EMPLOYEE")
                            .requestMatchers(HttpMethod.GET, "/api/courses/**").hasRole("EMPLOYEE")
                            .requestMatchers(HttpMethod.GET, "/api/instructors").hasRole("EMPLOYEE")
                            .requestMatchers(HttpMethod.GET, "/api/instructors/**").hasRole("EMPLOYEE")
                            .requestMatchers(HttpMethod.POST, "/api/courses").hasRole("MANAGER")
                            .requestMatchers(HttpMethod.POST, "/api/instructors").hasRole("MANAGER")
                            .requestMatchers(HttpMethod.PUT, "/api/courses/**").hasRole("MANAGER")
                            .requestMatchers(HttpMethod.PUT, "/api/instructors/**").hasRole("MANAGER")
                            .requestMatchers(HttpMethod.DELETE, "/api/courses/**").hasRole("ADMIN")
                            .requestMatchers(HttpMethod.DELETE, "/api/instructors/**").hasRole("ADMIN")
        				);

        // use HTTP Basic authentication
//        http.httpBasic(Customizer.withDefaults());
        http.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        // disable Cross Site Request Forgery (CSRF)
        // in general not required for stateless REST APIs that use POST, PUT, DELETE and/or PATCH
        http.csrf(csrf -> csrf.disable());

        return http.build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
    	return authenticationConfiguration.getAuthenticationManager();
    }
}
