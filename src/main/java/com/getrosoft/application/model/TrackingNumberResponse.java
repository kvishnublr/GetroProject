package com.getrosoft.application.model;

import lombok.Data;
import java.time.OffsetDateTime;

/**
 * Response model for tracking number generation.
 * 
 * @author Vishnu.K
 */
@Data
public class TrackingNumberResponse {
    /**
     * Generated unique tracking number.
     */
    private String tracking_number;
    
    /**
     * Creation timestamp.
     */
    private OffsetDateTime created_at;
}