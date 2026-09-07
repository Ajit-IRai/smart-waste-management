package com.swm.smartwaste.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository; 

import com.swm.smartwaste.Entity.User;

public interface UserRepository extends JpaRepository<User, Integer>{
	
	boolean existsByEmail(String email);
	
	Optional<User> findByEmail(String email);

}
