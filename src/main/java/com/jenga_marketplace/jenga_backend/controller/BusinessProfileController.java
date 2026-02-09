package com.jenga_marketplace.jenga_backend.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jenga_marketplace.jenga_backend.model.BusinessProfile;
import com.jenga_marketplace.jenga_backend.model.dto.BusinessOnboardingRequest;
import com.jenga_marketplace.jenga_backend.service.BusinessProfileService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/business")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:8081") // Matches your frontend port
public class BusinessProfileController {

    private final BusinessProfileService profileService;

    /**
     * Endpoint to save the SME's legal and initial financial data.
     * Strategic Value: Converts manual hardware shop data into IFRS-ready digital records.
     */
    @PostMapping("/onboard/{userId}")
    public ResponseEntity<?> onboardBusiness(@PathVariable Long userId, @RequestBody BusinessOnboardingRequest request) {
        try {
            BusinessProfile profile = profileService.createFullProfile(userId, request);
            return ResponseEntity.ok(profile);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Onboarding failed: " + e.getMessage());
        }
    }

    @GetMapping("/profile/{userId}")
    public ResponseEntity<BusinessProfile> getProfile(@PathVariable Long userId) {
        return ResponseEntity.ok(profileService.getProfileByUserId(userId));
    }
}