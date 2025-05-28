package com.getrosoft.application.service;

import com.getrosoft.application.model.TrackingNumberRequest;
import com.getrosoft.application.model.TrackingNumberResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.OffsetDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.regex.Pattern;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests for TrackingNumberService.
 * 
 * @author Vishnu.K
 */
class TrackingNumberServiceTest {

    private TrackingNumberService trackingNumberService;
    private TrackingNumberRequest request;

    /**
     * Setup test environment.
     */
    @BeforeEach
    void setUp() {
        trackingNumberService = new TrackingNumberService();
        
        // Create sample request
        request = new TrackingNumberRequest();
        request.setOriginCountryId("US");
        request.setDestinationCountryId("CA");
        request.setWeight("2.500");
        request.setCreatedAt(OffsetDateTime.now().toString());
        request.setCustomerId("de619854-b59b-425e-9db4-943979e1bd49");
        request.setCustomerName("Test Customer");
        request.setCustomerSlug("test-customer");
    }

    /**
     * Test basic tracking number generation.
     */
    @Test
    void generateUniqueTrackingNumber() {
        // When
        TrackingNumberResponse response = trackingNumberService.generateTrackingNumber(request);
        
        // Then
        assertNotNull(response);
        assertNotNull(response.getTracking_number());
        assertFalse(response.getTracking_number().isEmpty());
    }

    /**
     * Test response field validation.
     */
    @Test
    void verifyResponseFields() {
        // When
        TrackingNumberResponse response = trackingNumberService.generateTrackingNumber(request);
        
        // Then
        assertNotNull(response);
        assertNotNull(response.getTracking_number());
        assertNotNull(response.getCreated_at());
        
        // Verify timestamp is recent
        OffsetDateTime now = OffsetDateTime.now();
        assertTrue(response.getCreated_at().isAfter(now.minusSeconds(5)));
        assertTrue(response.getCreated_at().isBefore(now.plusSeconds(5)));
    }

    /**
     * Test concurrent request handling.
     */
    @Test
    void handleConcurrentRequests() throws InterruptedException {
        // Given
        int numberOfThreads = 10;
        ExecutorService executorService = Executors.newFixedThreadPool(numberOfThreads);
        CountDownLatch latch = new CountDownLatch(numberOfThreads);
        Set<String> trackingNumbers = new HashSet<>();
        
        // When
        for (int i = 0; i < numberOfThreads; i++) {
            executorService.submit(() -> {
                try {
                    TrackingNumberResponse response = trackingNumberService.generateTrackingNumber(request);
                    synchronized (trackingNumbers) {
                        trackingNumbers.add(response.getTracking_number());
                    }
                } finally {
                    latch.countDown();
                }
            });
        }
        
        // Wait for completion
        boolean completed = latch.await(10, TimeUnit.SECONDS);
        executorService.shutdown();
        
        // Then
        assertTrue(completed, "All threads should complete within the timeout");
        assertEquals(numberOfThreads, trackingNumbers.size(), "All tracking numbers should be unique");
    }

    /**
     * Test tracking number format.
     */
    @Test
    void verifyTrackingNumberFormat() {
        // When
        TrackingNumberResponse response = trackingNumberService.generateTrackingNumber(request);
        
        // Then
        String trackingNumber = response.getTracking_number();
        assertNotNull(trackingNumber);
        
        // Verify length
        assertEquals(14, trackingNumber.length());
        
        // Verify format (uppercase alphanumeric)
        assertTrue(Pattern.matches("^[A-Z0-9]{14}$", trackingNumber));
    }

    /**
     * Test uniqueness across multiple generations.
     */
    @Test
    void verifyTrackingNumberUniqueness() {
        // Given
        int numberOfRequests = 5;
        Set<String> trackingNumbers = new HashSet<>();
        
        // When
        for (int i = 0; i < numberOfRequests; i++) {
            TrackingNumberResponse response = trackingNumberService.generateTrackingNumber(request);
            trackingNumbers.add(response.getTracking_number());
        }
        
        // Then
        assertEquals(numberOfRequests, trackingNumbers.size(), "All tracking numbers should be unique");
    }
}