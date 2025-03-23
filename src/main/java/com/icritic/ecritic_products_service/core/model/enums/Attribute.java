package com.icritic.ecritic_products_service.core.model.enums;

import com.icritic.ecritic_products_service.exception.ResourceViolationException;

public enum Attribute {

    COLOR,
    SIZE,
    WEIGHT;

    public static Attribute parse(String attribute) {
        try {
            return Attribute.valueOf(attribute.toUpperCase());
        } catch (IllegalArgumentException ex) {
            throw new ResourceViolationException("Invalid attribute name");
        }
    }
}
