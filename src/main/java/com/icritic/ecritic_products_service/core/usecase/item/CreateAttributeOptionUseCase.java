package com.icritic.ecritic_products_service.core.usecase.item;

import com.icritic.ecritic_products_service.core.model.AttributeOption;
import com.icritic.ecritic_products_service.core.usecase.item.boundary.FindAttributeOptionBoundary;
import com.icritic.ecritic_products_service.core.usecase.item.boundary.SaveAttributeOptionBoundary;
import com.icritic.ecritic_products_service.exception.DefaultException;
import com.icritic.ecritic_products_service.exception.EntityConflictException;
import com.icritic.ecritic_products_service.exception.InternalErrorException;
import com.icritic.ecritic_products_service.exception.handler.ErrorResponseCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class CreateAttributeOptionUseCase {

    private final FindAttributeOptionBoundary findAttributeOptionBoundary;

    private final SaveAttributeOptionBoundary saveAttributeOptionBoundary;

    public AttributeOption execute(AttributeOption attributeOption) {
        log.info("Creating attribute option: [{}]", attributeOption.toString());

        try {
            Optional<AttributeOption> optionalAttributeOption = findAttributeOptionBoundary.execute(attributeOption.getAttribute(), attributeOption.getValue());

            if (optionalAttributeOption.isPresent()) {
                log.error("Attribute option already exists");
                throw new EntityConflictException(ErrorResponseCode.ECRITICPROD_16);
            }

            AttributeOption savedAttributeOption = saveAttributeOptionBoundary.execute(attributeOption);

            return savedAttributeOption;
        } catch (DefaultException ex) {
            log.error("Error creating attribute option. Exception: [{}]", ex.getErrorResponse());
            throw ex;
        } catch (Exception ex) {
            log.error("Error creating attribute option", ex);
            throw new InternalErrorException(ErrorResponseCode.ECRITICPROD_09);
        }
    }
}
