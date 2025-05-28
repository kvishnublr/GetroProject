package com.getrosoft.application.controller;

import com.getrosoft.application.model.TrackingNumberRequest;
import com.getrosoft.application.model.TrackingNumberResponse;
import com.getrosoft.application.service.TrackingNumberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * REST controller for tracking number generation.
 * 
 * @author Vishnu.K
 */
@RestController
public class TrackingNumberController {

    /**
     * Service for tracking number operations.
     */
    @Autowired
    private TrackingNumberService trackingNumberService;

    /**
     * Generates a new tracking number based on request parameters.
     */
    @GetMapping("/next-tracking-number")
    public ResponseEntity<TrackingNumberResponse> generateTrackingNumber(@RequestBody TrackingNumberRequest request) {
        TrackingNumberResponse response = trackingNumberService.generateTrackingNumber(request);
        return ResponseEntity.ok(response);
    }
}
