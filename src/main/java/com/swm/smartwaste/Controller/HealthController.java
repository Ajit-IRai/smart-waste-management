package com.swm.smartwaste.Controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HealthController {

	
	@GetMapping("/api/health")
	public String healthCheck() {
		return "Smart Waste Management API is running";
	}
}
