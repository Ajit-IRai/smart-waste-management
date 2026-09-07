package com.swm.smartwaste.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.swm.smartwaste.Entity.User;

public interface UserRepository extends JpaRepository<User, Long>{

}
