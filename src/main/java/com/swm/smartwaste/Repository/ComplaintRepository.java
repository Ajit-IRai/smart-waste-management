package com.swm.smartwaste.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.swm.smartwaste.Entity.Complaint;

public interface ComplaintRepository extends JpaRepository<Complaint, Integer> {
		
	List<Complaint> findByCitizen_Email(String email);
}