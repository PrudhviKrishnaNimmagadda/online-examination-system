package com.oes.online_examination_system.auth.dto;

public record LoginResponse(
	    String accessToken,
	    String tokenType,
	    long expiresIn
	) {

}
