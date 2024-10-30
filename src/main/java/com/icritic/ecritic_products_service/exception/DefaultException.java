package com.icritic.ecritic_products_service.exception;

import com.icritic.ecritic_products_service.exception.handler.ErrorResponse;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class DefaultException extends RuntimeException {

    private ErrorResponse errorResponse;
}
