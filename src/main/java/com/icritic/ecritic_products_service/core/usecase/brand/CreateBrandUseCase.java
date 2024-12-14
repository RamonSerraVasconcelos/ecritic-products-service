package com.icritic.ecritic_products_service.core.usecase.brand;

import com.icritic.ecritic_products_service.core.model.Brand;
import com.icritic.ecritic_products_service.core.usecase.brand.boundary.FindBrandByNameBoundary;
import com.icritic.ecritic_products_service.core.usecase.brand.boundary.InvalidateBrandsCacheBoundary;
import com.icritic.ecritic_products_service.core.usecase.brand.boundary.SaveBrandBoundary;
import com.icritic.ecritic_products_service.exception.DefaultException;
import com.icritic.ecritic_products_service.exception.EntityConflictException;
import com.icritic.ecritic_products_service.exception.InternalErrorException;
import com.icritic.ecritic_products_service.exception.handler.ErrorResponseCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class CreateBrandUseCase {

    private final FindBrandByNameBoundary findBrandByNameBoundary;

    private final SaveBrandBoundary saveBrandBoundary;

    private final InvalidateBrandsCacheBoundary invalidateBrandsCacheBoundary;

    public Brand execute(Brand brand) {
        log.info("Creating brand with name: [{}]", brand.getName());

        try {
            Optional<Brand> optionalBrand = findBrandByNameBoundary.execute(brand.getName());

            if (optionalBrand.isPresent()) {
                throw new EntityConflictException(ErrorResponseCode.ECRITICPROD_10);
            }

            brand.setCreatedAt(OffsetDateTime.now());
            brand.setUpdatedAt(OffsetDateTime.now());

            Brand createdBrand = saveBrandBoundary.execute(brand);

            invalidateBrandsCacheBoundary.execute();

            return createdBrand;
        } catch (DefaultException ex) {
            log.error("Error creating brand. Exception: [{}]", ex.getErrorResponse());
            throw ex;
        } catch (Exception ex) {
            log.error("Error creating user", ex);
            throw new InternalErrorException(ErrorResponseCode.ECRITICPROD_09);
        }
    }
}
