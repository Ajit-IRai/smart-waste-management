package com.swm.smartwaste.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.swm.smartwaste.Entity.Complaint;
import com.swm.smartwaste.Services.ComplaintServices;
import com.swm.smartwaste.dto.ResponseStructure;

@RestController
@RequestMapping("/complaints")
public class ComplaintController {

    @Autowired
    private ComplaintServices complaintServices;

    @PostMapping
    public ResponseEntity<ResponseStructure<Complaint>> addComplaint(
            @RequestBody Complaint complaint) {

        return complaintServices.addComplaint(complaint);
    }

    @GetMapping("/all")
    public ResponseEntity<ResponseStructure<List<Complaint>>> getAllComplaints() {

        return complaintServices.getAllComplaints();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseStructure<Complaint>> getComplaintById(
            @PathVariable Integer id) {

        return complaintServices.getComplaintById(id);
    }

    @PutMapping
    public ResponseEntity<ResponseStructure<Complaint>> updateComplaint(
            @RequestBody Complaint complaint) {

        return complaintServices.updateComplaint(complaint);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseStructure<String>> deleteComplaint(
            @PathVariable Integer id) {

        return complaintServices.deleteComplaint(id);
    }
    
    @GetMapping("/my")
    public ResponseEntity<ResponseStructure<List<Complaint>>> getMyComplaints() {

        return complaintServices.getMyComplaints();
    }
}