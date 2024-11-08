package com.icritic.ecritic_products_service.core.usecase.brand;

import com.icritic.ecritic_products_service.core.model.Brand;
import com.icritic.ecritic_products_service.core.usecase.brand.boundary.CacheBrandBoundary;
import com.icritic.ecritic_products_service.core.usecase.brand.boundary.FindBrandByIdBoundary;
import com.icritic.ecritic_products_service.core.usecase.brand.boundary.FindCachedBrandBoundary;
import com.icritic.ecritic_products_service.exception.DefaultException;
import com.icritic.ecritic_products_service.exception.EntityNotFoundException;
import com.icritic.ecritic_products_service.exception.InternalErrorException;
import com.icritic.ecritic_products_service.exception.handler.ErrorResponseCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
@Slf4j
public class FindBrandByIdUseCase {

    private final FindCachedBrandBoundary findCachedBrandBoundary;

    private final FindBrandByIdBoundary findBrandByIdBoundary;

    private final CacheBrandBoundary cacheBrandBoundary;

    public Brand execute(Long id) {
        try {
            Optional<Brand> cachedBrand = findBrandByIdBoundary.execute(id);

            if (cachedBrand.isPresent()) {
                log.info("Returning brand from cache");
                return cachedBrand.get();
            }

            Optional<Brand> brand = findCachedBrandBoundary.execute(id);

            if (brand.isEmpty()) {
                throw new EntityNotFoundException(ErrorResponseCode.ECRITICPROD_11);
            }

            return brand.get();
        } catch (DefaultException ex) {
            log.error("Error finding brand by id. Exception: [{}]", ex.getErrorResponse());
            throw ex;
        } catch (Exception ex) {
            log.error("Error finding brand by id", ex);
            throw new InternalErrorException(ErrorResponseCode.ECRITICPROD_09);
        }
    }
}
