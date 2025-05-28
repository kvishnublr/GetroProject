package com.getrosoft.application.controller;

import com.getrosoft.application.model.TrackingNumberRequest;
import com.getrosoft.application.model.TrackingNumberResponse;
import com.getrosoft.application.service.TrackingNumberService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.time.OffsetDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

/**
 * Tests for TrackingNumberController.
 * 
 * @author Vishnu.K
 */
public class TrackingNumberControllerTest {

    @InjectMocks
    private TrackingNumberController trackingNumberController;

    @Mock
    private TrackingNumberService trackingNumberService;

    /**
     * Setup mocks.
     */
    public TrackingNumberControllerTest() {
        MockitoAnnotations.openMocks(this);
    }

    /**
     * Test tracking number generation endpoint.
     */
    @Test
    public void testGenerateTrackingNumber() {
        // Arrange
        TrackingNumberRequest request = new TrackingNumberRequest();
        request.setOriginCountryId("MY");
        request.setDestinationCountryId("ID");
        request.setWeight("1.234");
        request.setCreatedAt(OffsetDateTime.now().toString());
        request.setCustomerId("de619854-b59b-425e-9db4-943979e1bd49");
        request.setCustomerName("RedBox Logistics");
        request.setCustomerSlug("redbox-logistics");

        TrackingNumberResponse response = new TrackingNumberResponse();
        response.setTracking_number("ABC1234567890123");
        response.setCreated_at(OffsetDateTime.now());

        when(trackingNumberService.generateTrackingNumber(request)).thenReturn(response);

        // Act
        ResponseEntity<TrackingNumberResponse> result = trackingNumberController.generateTrackingNumber(request);
        
        // Assert
        assertEquals(200, result.getStatusCodeValue());
        assertEquals("ABC1234567890123", result.getBody().getTracking_number());
    }
}