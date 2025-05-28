package com.getrosoft.application.service;

import com.getrosoft.application.model.TrackingNumberRequest;
import com.getrosoft.application.model.TrackingNumberResponse;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Service for generating unique tracking numbers.
 * Uses thread-safe approach for concurrent requests.
 * 
 * @author Vishnu.K
 */
@Service
public class TrackingNumberService {
    /**
     * Thread-safe map to store generated tracking numbers.
     */
    private final ConcurrentHashMap<String, Boolean> trackingNumbers = new ConcurrentHashMap<>();

    /**
     * Generates a unique tracking number.
     * Uses UUID to create random alphanumeric identifiers.
     * 
     * @param request Contains shipment details
     * @return Response with tracking number and timestamp
     */
    public TrackingNumberResponse generateTrackingNumber(TrackingNumberRequest request) {
        String trackingNumber;
        do {
            // Generate random tracking number (14 chars, uppercase)
            trackingNumber = UUID.randomUUID().toString().substring(0, 16).toUpperCase().replaceAll("-", "");
        } while (trackingNumbers.putIfAbsent(trackingNumber, true) != null);

        // Create response
        TrackingNumberResponse response = new TrackingNumberResponse();
        response.setTracking_number(trackingNumber);
        response.setCreated_at(OffsetDateTime.now());
        return response;
    }
}