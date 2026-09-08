	package com.swm.smartwaste.Entity;
	
	import java.time.LocalDateTime;
	
	import com.swm.smartwaste.Enum.AssignmentStatus;
	import com.swm.smartwaste.Enum.ComplaintStatus;
	import com.swm.smartwaste.Enum.Priority;
	import com.swm.smartwaste.Enum.WasteType;
	import jakarta.persistence.JoinColumn;
	import jakarta.persistence.ManyToOne;
	import jakarta.persistence.Entity;
	import jakarta.persistence.EnumType;
	import jakarta.persistence.Enumerated;
	import jakarta.persistence.GeneratedValue;
	import jakarta.persistence.GenerationType;
	import jakarta.persistence.Id;
	import jakarta.persistence.PrePersist;
	import jakarta.persistence.Table;
	
	@Entity
	@Table(name = "complaints")
	public class Complaint {
	
	    @Id
	    @GeneratedValue(strategy = GenerationType.SEQUENCE)
	    private Integer id;
	
	    private String complaintNumber;
	
	    private String description;
	
	    @Enumerated(EnumType.STRING)
	    private WasteType wasteType;
	
	    @Enumerated(EnumType.STRING)
	    private Priority priority;
	
	    @Enumerated(EnumType.STRING)
	    private ComplaintStatus complaintStatus;
	
	    @Enumerated(EnumType.STRING)
	    private AssignmentStatus assignmentStatus;
	
	    private LocalDateTime createdAt;
	    
	    @ManyToOne
	    @JoinColumn(name = "citizen_id")
	    private User citizen;
	
	    
	    
	    @PrePersist
	    protected void onCreate() {
	        createdAt = LocalDateTime.now();
	    }
	
	    
	    
	    public Integer getId() {
	        return id;
	    }
	
	    public void setId(Integer id) {
	        this.id = id;
	    }
	
	    public String getComplaintNumber() {
	        return complaintNumber;
	    }
	
	    public void setComplaintNumber(String complaintNumber) {
	        this.complaintNumber = complaintNumber;
	    }
	
	    public String getDescription() {
	        return description;
	    }
	
	    public void setDescription(String description) {
	        this.description = description;
	    }
	
	    public WasteType getWasteType() {
	        return wasteType;
	    }
	
	    public void setWasteType(WasteType wasteType) {
	        this.wasteType = wasteType;
	    }
	
	    public Priority getPriority() {
	        return priority;
	    }
	
	    public void setPriority(Priority priority) {
	        this.priority = priority;
	    }
	
	    public ComplaintStatus getComplaintStatus() {
	        return complaintStatus;
	    }
	
	    public void setComplaintStatus(ComplaintStatus complaintStatus) {
	        this.complaintStatus = complaintStatus;
	    }
	
	    public AssignmentStatus getAssignmentStatus() {
	        return assignmentStatus;
	    }
	
	    public void setAssignmentStatus(AssignmentStatus assignmentStatus) {
	        this.assignmentStatus = assignmentStatus;
	    }
	
	    public LocalDateTime getCreatedAt() {
	        return createdAt;
	    }
	
	    public void setCreatedAt(LocalDateTime createdAt) {
	        this.createdAt = createdAt;
	    }
	    
	    public User getCitizen() {
	        return citizen;
	    }
	
	    public void setCitizen(User citizen) {
	        this.citizen = citizen;
	    }
	}