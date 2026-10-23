package com.oes.online_examination_system.auth.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record LoginRequest(
		@NotBlank(message = "Email is required")
		@Email(message = "Enter a valid email address")
		String email,
		
		@NotBlank(message = "password is required")
		String password
		) {

}
