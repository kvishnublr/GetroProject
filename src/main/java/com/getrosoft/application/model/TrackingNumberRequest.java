package com.getrosoft.application.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

/**
 * Request model for tracking number generation.
 * 
 * @author Vishnu.K
 */
@Data
public class TrackingNumberRequest {
    /**
     * Two-letter ISO country code for origin (e.g., "US").
     */
    @NotBlank(message = "Origin country ID must not be blank")
    @Pattern(regexp = "^[A-Z]{2}$", message = "Origin country ID must be two uppercase letters")
    private String originCountryId;

    /**
     * Two-letter ISO country code for destination (e.g., "UK").
     */
    @NotBlank(message = "Destination country ID must not be blank")
    @Pattern(regexp = "^[A-Z]{2}$", message = "Destination country ID must be two uppercase letters")
    private String destinationCountryId;

    /**
     * Weight with up to three decimal places.
     */
    @NotBlank(message = "Weight must not be blank")
    @Pattern(regexp = "^\\d+(\\.\\d{1,3})?$", message = "Weight must be a number with up to three decimal places")
    private String weight;

    /**
     * ISO 8601 timestamp (e.g., "2023-01-01T12:00:00Z").
     */
    @NotBlank(message = "Created at must not be blank")
    @Pattern(
            regexp = "^\\d{4}-\\d{2}-\\d{2}T\\d{2}:\\d{2}:\\d{2}(Z|[+-]\\d{2}:\\d{2})$",
            message = "Created at must be in ISO 8601 format (e.g., 2023-01-01T12:00:00Z)"
    )
    private String createdAt;

    /**
     * Customer UUID.
     */
    @NotBlank(message = "Customer ID must not be blank")
    @Pattern(
            regexp = "^[0-9a-fA-F]{8}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{12}$",
            message = "Customer ID must be a valid UUID"
    )
    private String customerId;

    /**
     * Customer's full name.
     */
    @NotBlank(message = "Customer name must not be blank")
    private String customerName;

    /**
     * URL-friendly customer identifier.
     */
    @NotBlank(message = "Customer slug must not be blank")
    @Pattern(
            regexp = "^[a-z0-9]+(?:-[a-z0-9]+)*$",
            message = "Customer slug must be lowercase alphanumeric and may contain hyphens"
    )
    private String customerSlug;
}