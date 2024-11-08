package com.icritic.ecritic_products_service.core.usecase.brand;

import com.icritic.ecritic_products_service.core.model.BrandFilter;
import com.icritic.ecritic_products_service.core.usecase.brand.boundary.CacheBrandsCountBoundary;
import com.icritic.ecritic_products_service.core.usecase.brand.boundary.CountBrandsBoundary;
import com.icritic.ecritic_products_service.core.usecase.brand.boundary.FindCachedBrandsCountBoundary;
import com.icritic.ecritic_products_service.exception.DefaultException;
import com.icritic.ecritic_products_service.exception.InternalErrorException;
import com.icritic.ecritic_products_service.exception.handler.ErrorResponseCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import static java.util.Objects.nonNull;

@Service
@RequiredArgsConstructor
@Slf4j
public class CountBrandsUseCase {

    private final FindCachedBrandsCountBoundary findCachedBrandsCountBoundary;

    private final CountBrandsBoundary countBrandsBoundary;

    private final CacheBrandsCountBoundary cacheBrandsCountBoundary;

    public Long execute(BrandFilter brandFilter) {
        log.info("Count all brands");

        try {
            if (brandFilter.isCacheable()) {
                Long count = findCachedBrandsCountBoundary.execute();

                if (nonNull(count)) {
                    return count;
                }
            }

            Long count = countBrandsBoundary.execute(brandFilter);

            cacheBrandsCountBoundary.execute(count);

            return count;
        } catch (DefaultException ex) {
            log.error("Error counting brands. Exception: [{}]", ex.getErrorResponse());
            throw ex;
        } catch (Exception ex) {
            log.error("Error counting brands", ex);
            throw new InternalErrorException(ErrorResponseCode.ECRITICPROD_09);
        }
    }
}
