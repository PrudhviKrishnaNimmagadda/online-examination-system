package com.oes.online_examination_system.config;

import org.springframework.context.annotation.Bean;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
//import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.oes.online_examination_system.auth.repository.UserRepository;
import com.oes.online_examination_system.auth.service.JwtService;


@Configuration
@EnableWebSecurity
public class SecurityConfig {
	@Bean
	public SecurityFilterChain securityFilterChain(
			HttpSecurity http,
			JwtService jwtService,
			UserRepository userRepository) throws Exception{
		
		JwtAuthenticationFilter jwtFilter = 
				new JwtAuthenticationFilter(jwtService, userRepository);
		
		http
			.csrf(csrf -> csrf.disable())
			.sessionManagement(session -> session
					.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
			.authorizeHttpRequests(auth -> auth
					.requestMatchers(
							HttpMethod.POST,
							"/api/v1/auth/register",
		                    "/api/v1/auth/login"

					).permitAll()
					.anyRequest().authenticated()
				)
			.addFilterBefore(
					jwtFilter,
					UsernamePasswordAuthenticationFilter.class);
		
		return http.build();
	}

}
