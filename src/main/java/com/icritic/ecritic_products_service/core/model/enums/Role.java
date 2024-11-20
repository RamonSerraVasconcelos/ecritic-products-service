package com.icritic.ecritic_products_service.core.model.enums;

import com.icritic.ecritic_products_service.exception.ResourceViolationException;

public enum Role {

    DEFAULT,
    MODERATOR,
    ADMIN;

    public static Role parseRole(String role) {
        try {
            return Role.valueOf(role.toUpperCase());
        } catch (Exception e) {
            throw new ResourceViolationException("Invalid role");
        }
    }
}
