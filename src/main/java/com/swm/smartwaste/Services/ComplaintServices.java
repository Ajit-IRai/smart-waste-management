package com.swm.smartwaste.Services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import com.swm.smartwaste.Dao.UserDao;
import com.swm.smartwaste.Entity.User;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import com.swm.smartwaste.Dao.ComplaintDao;
import com.swm.smartwaste.Entity.Complaint;
import com.swm.smartwaste.Enum.AssignmentStatus;
import com.swm.smartwaste.Enum.ComplaintStatus;
import com.swm.smartwaste.Enum.Priority;
import com.swm.smartwaste.dto.ResponseStructure;

@Service
public class ComplaintServices {

    @Autowired
    private ComplaintDao complaintDao;
    
    @Autowired
    private UserDao userDao;

    public ResponseEntity<ResponseStructure<Complaint>> addComplaint(Complaint complaint) {

        ResponseStructure<Complaint> response = new ResponseStructure<>();

        if (complaint.getDescription() == null
                || complaint.getDescription().isBlank()
                || complaint.getWasteType() == null) {

            response.setStatusCode(HttpStatus.BAD_REQUEST.value());
            response.setMessage("Description and waste type are required");
            response.setData(null);

            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }

        Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null
                || !authentication.isAuthenticated()
                || authentication.getName() == null) {

            response.setStatusCode(HttpStatus.UNAUTHORIZED.value());
            response.setMessage("Please login to create a complaint");
            response.setData(null);

            return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
        }

        String email = authentication.getName();

        Optional<User> optionalUser = userDao.findByEmail(email);

        if (optionalUser.isEmpty()) {

            response.setStatusCode(HttpStatus.NOT_FOUND.value());
            response.setMessage("Citizen not found");
            response.setData(null);

            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }

        User citizen = optionalUser.get();

        complaint.setCitizen(citizen);

        complaint.setDescription(complaint.getDescription().trim());

        complaint.setComplaintStatus(ComplaintStatus.REPORTED);
        complaint.setAssignmentStatus(AssignmentStatus.UNASSIGNED);

        if (complaint.getPriority() == null) {
            complaint.setPriority(Priority.MEDIUM);
        }

        Complaint savedComplaint = complaintDao.addComplaint(complaint);

        String complaintNumber = String.format(
                "SWM-%06d",
                savedComplaint.getId()
        );

        savedComplaint.setComplaintNumber(complaintNumber);

        savedComplaint = complaintDao.updateComplaint(savedComplaint);

        response.setStatusCode(HttpStatus.CREATED.value());
        response.setMessage("Complaint created successfully");
        response.setData(savedComplaint);

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }
    public ResponseEntity<ResponseStructure<List<Complaint>>> getAllComplaints() {

        ResponseStructure<List<Complaint>> response = new ResponseStructure<>();

        List<Complaint> complaints = complaintDao.getAllComplaints();

        if (complaints.isEmpty()) {

            response.setStatusCode(HttpStatus.NOT_FOUND.value());
            response.setMessage("No complaints found");
            response.setData(null);

            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }

        response.setStatusCode(HttpStatus.OK.value());
        response.setMessage("Complaints fetched successfully");
        response.setData(complaints);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    public ResponseEntity<ResponseStructure<Complaint>> getComplaintById(Integer id) {

        ResponseStructure<Complaint> response = new ResponseStructure<>();

        Optional<Complaint> optionalComplaint = complaintDao.getComplaintById(id);

        if (optionalComplaint.isEmpty()) {

            response.setStatusCode(HttpStatus.NOT_FOUND.value());
            response.setMessage("Complaint not found");
            response.setData(null);

            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }

        response.setStatusCode(HttpStatus.OK.value());
        response.setMessage("Complaint fetched successfully");
        response.setData(optionalComplaint.get());

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    public ResponseEntity<ResponseStructure<Complaint>> updateComplaint(Complaint complaint) {

        ResponseStructure<Complaint> response = new ResponseStructure<>();

        if (complaint.getId() == null) {

            response.setStatusCode(HttpStatus.BAD_REQUEST.value());
            response.setMessage("Complaint ID is required");
            response.setData(null);

            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }

        Optional<Complaint> optionalComplaint =
                complaintDao.getComplaintById(complaint.getId());

        if (optionalComplaint.isEmpty()) {

            response.setStatusCode(HttpStatus.NOT_FOUND.value());
            response.setMessage("Complaint not found");
            response.setData(null);

            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }

        Complaint existingComplaint = optionalComplaint.get();

        if (complaint.getDescription() != null
                && !complaint.getDescription().isBlank()) {

            existingComplaint.setDescription(complaint.getDescription().trim());
        }

        if (complaint.getWasteType() != null) {
            existingComplaint.setWasteType(complaint.getWasteType());
        }

        if (complaint.getPriority() != null) {
            existingComplaint.setPriority(complaint.getPriority());
        }

        if (complaint.getComplaintStatus() != null) {
            existingComplaint.setComplaintStatus(complaint.getComplaintStatus());
        }

        if (complaint.getAssignmentStatus() != null) {
            existingComplaint.setAssignmentStatus(complaint.getAssignmentStatus());
        }

        response.setStatusCode(HttpStatus.OK.value());
        response.setMessage("Complaint updated successfully");
        response.setData(complaintDao.updateComplaint(existingComplaint));

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    public ResponseEntity<ResponseStructure<String>> deleteComplaint(Integer id) {

        ResponseStructure<String> response = new ResponseStructure<>();

        Optional<Complaint> optionalComplaint =
                complaintDao.getComplaintById(id);

        if (optionalComplaint.isEmpty()) {

            response.setStatusCode(HttpStatus.NOT_FOUND.value());
            response.setMessage("Complaint not found");
            response.setData(null);

            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }

        complaintDao.deleteComplaint(optionalComplaint.get());

        response.setStatusCode(HttpStatus.OK.value());
        response.setMessage("Complaint deleted successfully");
        response.setData("Complaint with ID " + id + " deleted successfully");

        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    
    public ResponseEntity<ResponseStructure<List<Complaint>>> getMyComplaints() {

        ResponseStructure<List<Complaint>> response = new ResponseStructure<>();

        Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null
                || !authentication.isAuthenticated()
                || authentication.getName() == null) {

            response.setStatusCode(HttpStatus.UNAUTHORIZED.value());
            response.setMessage("Please login to view your complaints");
            response.setData(null);

            return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
        }

        String email = authentication.getName();

        List<Complaint> complaints =
                complaintDao.getComplaintsByCitizenEmail(email);

        if (complaints.isEmpty()) {

            response.setStatusCode(HttpStatus.NOT_FOUND.value());
            response.setMessage("No complaints found");
            response.setData(null);

            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }

        response.setStatusCode(HttpStatus.OK.value());
        response.setMessage("Your complaints fetched successfully");
        response.setData(complaints);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}