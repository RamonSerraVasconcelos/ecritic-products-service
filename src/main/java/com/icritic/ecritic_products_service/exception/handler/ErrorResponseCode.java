package com.icritic.ecritic_products_service.exception.handler;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum ErrorResponseCode {

    ECRITICPROD_01("ECRITICPROD-01", "Invalid request data", "Invalid request data"),
    ECRITICPROD_02("ECRITICPROD-02", "Missing required headers", "Missing required headers"),
    ECRITICPROD_03("ECRITICPROD-03", "Unauthorized access", "Unauthorized access"),
    ECRITICPROD_04("ECRITICPROD-04", "Resource not found", "Resource not found"),
    ECRITICPROD_05("ECRITICPROD-05", "Resource conflict", "Resource conflict"),
    ECRITICPROD_06("ECRITICPROD-06", "Resource violation", "Resource violation"),
    ECRITICPROD_07("ECRITICPROD-07", "Unauthorized", "Invalid authorization token"),
    ECRITICPROD_08("ECRITICPROD-08", "Forbidden access", "Forbidden access to the requested resource"),
    ECRITICPROD_09("ECRITICPROD-09", "Internal server error", "An internal error occured while executing this operation"),
    ECRITICPROD_10("ECRITICPROD-10", "Brand conflict", "A brand with the requested name already exists");

    private final String code;
    private final String message;
    private final String detail;
}
