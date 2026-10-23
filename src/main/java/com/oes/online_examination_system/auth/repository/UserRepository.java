package com.oes.online_examination_system.auth.repository;

import com.oes.online_examination_system.auth.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface UserRepository  extends JpaRepository<User, Long>{
	
	Optional<User> findByEmail(String email);
	
	boolean existsByEmail(String email);
	

}
