package com.oes.online_examination_system.auth;

import jakarta.persistence.*;
import java.time.Instant;

@Entity
@Table(
		name = "app_users",
		uniqueConstraints = {
				@UniqueConstraint(
					name = "uk_app_users_email",
					columnNames = "email"
					)
		}
		)

public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "full_name", nullable = false, length = 100)
	private String fullName;
	
	@Column(nullable = false, length = 150)
	private String email;
	
	@Column(name = "password_hash", nullable = false, length = 255)
	private String passwordHash;
	
	@Enumerated(EnumType.STRING)
	@Column(nullable = false, length = 20)
	private Role role;
	
	@Column(nullable = false)
	private boolean enabled = true;
	
	@Column(name = "created_at", nullable = false)
	private Instant createdAt;
	
	public User() {
		// TODO Auto-generated constructor stub
	}
	
	@PrePersist
	protected void onCreate() {
		if(createdAt == null) {
			createdAt = Instant.now();
		}
	}
	
	public Long getId() {
		return id;
	}
	
	public String getFullName() {
		return fullName;
	}
	
	public void setFullName(String fullName) {
		this.fullName = fullName;
		
	}
	public String getEmail() {
		return email;
		
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPasswordHash() {
		return passwordHash;
	}
	public void setPasswordHash(String passwordHash) {
		this.passwordHash = passwordHash;
	}
	
	public Role getRole() {
		return role;
	}
	public void setRole(Role role) {
		this.role = role;
	}
	
	public boolean isEnabled() {
		return enabled;
	}
	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}
	public Instant getCreatedAt() {
		return createdAt;
	}

}
