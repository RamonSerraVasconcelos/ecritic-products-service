package com.icritic.ecritic_products_service.core.usecase.brand;

import com.icritic.ecritic_products_service.core.model.Brand;
import com.icritic.ecritic_products_service.core.usecase.brand.boundary.FindBrandByNameBoundary;
import com.icritic.ecritic_products_service.core.usecase.brand.boundary.SaveBrandBoundary;
import com.icritic.ecritic_products_service.exception.BusinessViolationException;
import com.icritic.ecritic_products_service.exception.DefaultException;
import com.icritic.ecritic_products_service.exception.InternalErrorException;
import com.icritic.ecritic_products_service.exception.handler.ErrorResponseCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class CreateBrandUseCase {

    private final FindBrandByNameBoundary findBrandByNameBoundary;

    private final SaveBrandBoundary saveBrandBoundary;

    public Brand execute(Brand brand) {
        try {
            Optional<Brand> optionalBrand = findBrandByNameBoundary.execute(brand.getName());

            if (optionalBrand.isPresent()) {
                throw new BusinessViolationException(ErrorResponseCode.ECRITICPROD_10);
            }

            return saveBrandBoundary.execute(brand);
        } catch (DefaultException ex) {
            log.error("Error creating brand. Exception: [{}]", ex.getErrorResponse());
            throw ex;
        } catch (Exception ex) {
            log.error("Error creating user", ex);
            throw new InternalErrorException(ErrorResponseCode.ECRITICPROD_09);
        }
    }
}
