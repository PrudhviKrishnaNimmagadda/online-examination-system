package com.oes.online_examination_system.auth.service;

import com.oes.online_examination_system.auth.Role;


import com.oes.online_examination_system.auth.User;
import com.oes.online_examination_system.auth.dto.RegisterRequest;
import com.oes.online_examination_system.auth.repository.UserRepository;

import com.oes.online_examination_system.auth.dto.LoginRequest;
import com.oes.online_examination_system.auth.dto.LoginResponse;
//import com.oes.online_examination_system.auth.service.JwtService;
import org.springframework.http.HttpStatus;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import org.springframework.web.server.ResponseStatusException;

import java.util.Locale;

@Service
public class AuthService {
	
	private final UserRepository userRepository;
	private final JwtService jwtService;
	private final PasswordEncoder passwordEncoder;
	
	public AuthService(
			UserRepository userRepository,
			PasswordEncoder passwordEncoder,
			JwtService jwtService) {
		this.userRepository = userRepository;
		this.passwordEncoder = passwordEncoder;
		this.jwtService = jwtService;
	
	}
	
	@Transactional
	public long registerCandidate(RegisterRequest request) {
		
		String email = request.email()
				.trim()
				.toLowerCase(Locale.ROOT);
		if(userRepository.existsByEmail(email)) {
			throw new IllegalArgumentException("An account with this email already exists");
			
		}
		User user = new User();
		user.setFullName(request.fullName().trim());
		user.setEmail(email);
		user.setPasswordHash(passwordEncoder.encode(request.password()));
		user.setRole(Role.CANDIDATE);
		user.setEnabled(true);
		
		User savedUser = userRepository.save(user);
		 return savedUser.getId();
		}
	
	

	@Transactional(readOnly = true)
	public LoginResponse login(LoginRequest request) {
		String email = request.email().trim().toLowerCase(Locale.ROOT);

		User user = userRepository.findByEmail(email)
            .orElseThrow(() -> new ResponseStatusException(
                    HttpStatus.UNAUTHORIZED,
                    "Invalid email or password"
            ));

    if (!passwordEncoder.matches(
            request.password(), user.getPasswordHash())) {
        throw new ResponseStatusException(
                HttpStatus.UNAUTHORIZED,
                "Invalid email or password"
        );
    }

    if (!user.isEnabled()) {
        throw new ResponseStatusException(
                HttpStatus.FORBIDDEN,
                "Account is disabled"
        );
    }

    String token = jwtService.generateToken(user);

    return new LoginResponse(token, "Bearer", 900000L);
}

}


