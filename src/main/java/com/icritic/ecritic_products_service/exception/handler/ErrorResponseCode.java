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
    ECRITICPROD_10("ECRITICPROD-10", "Brand conflict", "A brand with the requested name already exists"),
    ECRITICPROD_11("ECRITICPROD-11", "Brand not found", "The requested brand does not exist"),
    ECRITICPROD_12("ECRITICPROD-12", "Category conflict", "A category with the requested name already exists"),
    ECRITICPROD_13("ECRITICPROD-13", "Category not found", "The requested category does not exist"),
    ECRITICPROD_14("ECRITICPROD-14", "Product conflict", "A product with the requested name already exists"),
    ECRITICPROD_15("ECRITICPROD-15", "Product not found", "The requested product does not exist"),
    ECRITICPROD_16("ECRITICPROD-16", "Attribute conflict", "The attribute option already exists"),
    ECRITICPROD_17("ECRITICPROD-17", "Attribute not found", "The requested attribute does not exist"),
    ECRITICPROD_18("ECRITICPROD-18", "Item conflict", "An item with the requested SKU already exists"),
    ECRITICPROD_19("ECRITICPROD-19", "Item not found", "The requested item does not exist"),
    ECRITICPROD_20("ECRITICPROD-20", "Item without attributes", "Item must have at least one attribute");

    private final String code;
    private final String message;
    private final String detail;
}
