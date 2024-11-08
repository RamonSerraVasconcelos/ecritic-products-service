package com.icritic.ecritic_products_service.core.usecase.brand;

import com.icritic.ecritic_products_service.core.model.Brand;
import com.icritic.ecritic_products_service.core.usecase.brand.boundary.FindBrandByIdBoundary;
import com.icritic.ecritic_products_service.core.usecase.brand.boundary.SaveBrandBoundary;
import com.icritic.ecritic_products_service.exception.DefaultException;
import com.icritic.ecritic_products_service.exception.EntityNotFoundException;
import com.icritic.ecritic_products_service.exception.InternalErrorException;
import com.icritic.ecritic_products_service.exception.handler.ErrorResponseCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class UpdateBrandUseCase {

    private final FindBrandByIdBoundary findBrandByIdBoundary;

    private final SaveBrandBoundary saveBrandBoundary;

    public Brand execute(Long id, Brand brand) {
        log.info("Updating brand with id: [{}]", id);

        try {
            Optional<Brand> optionalBrand = findBrandByIdBoundary.execute(id);

            if (optionalBrand.isEmpty()) {
                throw new EntityNotFoundException(ErrorResponseCode.ECRITICPROD_11);
            }

            Brand brandToBeUpdated = optionalBrand.get();

            brandToBeUpdated.setName(brand.getName());
            brandToBeUpdated.setDescription(brand.getDescription());

            return saveBrandBoundary.execute(brandToBeUpdated);
        } catch (DefaultException ex) {
            log.error("Error updating brand. Exception: [{}]", ex.getErrorResponse());
            throw ex;
        } catch (Exception ex) {
            log.error("Error updating brand", ex);
            throw new InternalErrorException(ErrorResponseCode.ECRITICPROD_09);
        }
    }
}
