package com.oes.online_examination_system.auth;

import com.oes.online_examination_system.auth.dto.RegisterRequest;
import com.oes.online_examination_system.auth.dto.LoginRequest;
import com.oes.online_examination_system.auth.dto.LoginResponse;
import com.oes.online_examination_system.auth.service.AuthService;

import jakarta.validation.Valid;

import org.springframework.http.HttpStatus;
//import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/v1/auth")

public class AuthController {
	private final AuthService authService;
	
	public AuthController(AuthService authService) {
		this.authService = authService;
	}
	@PostMapping("/register")
	public ResponseEntity<Map<String, Object>> register(
			@Valid @RequestBody RegisterRequest request){
		Long userId = authService.registerCandidate(request);
		
		return ResponseEntity
				.status(HttpStatus.CREATED)
				.body(Map.of(
						"message", "Candidate registered successfully",
						"userId", userId
						));
	}
	
	@PostMapping("/login")
	public ResponseEntity<LoginResponse> login(
	        @Valid @RequestBody LoginRequest request) {

	    LoginResponse response = authService.login(request);
	    return ResponseEntity.ok(response);
	}
	
	
	

}

