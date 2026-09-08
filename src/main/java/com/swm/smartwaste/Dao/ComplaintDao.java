package com.swm.smartwaste.Dao;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.swm.smartwaste.Entity.Complaint;
import com.swm.smartwaste.Repository.ComplaintRepository;

@Repository
public class ComplaintDao {

    @Autowired
    private ComplaintRepository complaintRepository;

    public Complaint addComplaint(Complaint complaint) {
        return complaintRepository.save(complaint);
    }

    public List<Complaint> getAllComplaints() {
        return complaintRepository.findAll();
    }

    public Optional<Complaint> getComplaintById(Integer id) {
        return complaintRepository.findById(id);
    }

    public Complaint updateComplaint(Complaint complaint) {
        return complaintRepository.save(complaint);
    }

    public void deleteComplaint(Complaint complaint) {
        complaintRepository.delete(complaint);
    }
    
    public List<Complaint> getComplaintsByCitizenEmail(String email) {
        return complaintRepository.findByCitizen_Email(email);
    }
}