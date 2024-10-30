package com.icritic.ecritic_products_service.exception;

import com.icritic.ecritic_products_service.exception.handler.ErrorResponse;
import com.icritic.ecritic_products_service.exception.handler.ErrorResponseCode;

public class EntityNotFoundException extends DefaultException {

    public EntityNotFoundException(ErrorResponseCode errorResponseCode) {
        super(ErrorResponse.builder()
                .code(errorResponseCode.getCode())
                .message(errorResponseCode.getMessage())
                .detail(errorResponseCode.getDetail())
                .build());
    }
}
