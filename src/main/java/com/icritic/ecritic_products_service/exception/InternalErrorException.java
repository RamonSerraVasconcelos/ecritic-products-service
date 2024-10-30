package com.icritic.ecritic_products_service.exception;

import com.icritic.ecritic_products_service.exception.handler.ErrorResponse;
import com.icritic.ecritic_products_service.exception.handler.ErrorResponseCode;

public class InternalErrorException extends DefaultException {

    public InternalErrorException(ErrorResponseCode errorResponseCode) {
        super(ErrorResponse.builder()
                .code(errorResponseCode.getCode())
                .message(errorResponseCode.getMessage())
                .detail(errorResponseCode.getDetail())
                .build());
    }
}
