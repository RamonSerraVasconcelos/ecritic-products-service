package com.icritic.ecritic_products_service.core.usecase.brand;

import com.icritic.ecritic_products_service.core.model.Brand;
import com.icritic.ecritic_products_service.core.model.BrandFilter;
import com.icritic.ecritic_products_service.core.usecase.brand.boundary.CacheBrandsBoundary;
import com.icritic.ecritic_products_service.core.usecase.brand.boundary.FindBrandsByParamsBoundary;
import com.icritic.ecritic_products_service.core.usecase.brand.boundary.FindCachedBrandsBoundary;
import com.icritic.ecritic_products_service.exception.DefaultException;
import com.icritic.ecritic_products_service.exception.InternalErrorException;
import com.icritic.ecritic_products_service.exception.handler.ErrorResponseCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class FindBrandsUseCase {

    private final FindCachedBrandsBoundary findCachedBrandsBoundary;

    private final FindBrandsByParamsBoundary findBrandsByParamsBoundary;

    private final CountBrandsUseCase countBrandsUseCase;

    private final CacheBrandsBoundary cacheBrandsBoundary;

    private boolean shouldCacheBrands;

    public Page<Brand> execute(BrandFilter brandFilter) {
        log.info("Finding all brands with parameters: [{}]", brandFilter);

        try {
            List<Brand> brands = getBrands(brandFilter);

            Long count = countBrandsUseCase.execute(brandFilter);

            Page<Brand> pageBrands = new PageImpl<>(brands, brandFilter.getPageable(), count);

            if (brandFilter.isCacheable() && shouldCacheBrands) {
                cacheBrandsBoundary.execute(pageBrands);
            }

            return pageBrands;
        } catch (DefaultException ex) {
            log.error("Error finding brands. Exception: [{}]", ex.getErrorResponse());
            throw ex;
        } catch (Exception ex) {
            log.error("Error finding brands", ex);
            throw new InternalErrorException(ErrorResponseCode.ECRITICPROD_09);
        }
    }

    private List<Brand> getBrands(BrandFilter brandFilter) {
        List<Brand> brands = new ArrayList<>();

        if (brandFilter.isCacheable()) {
            brands = findCachedBrandsBoundary.execute(brandFilter.getPageable());
            shouldCacheBrands = false;
        }

        if (brands.isEmpty()) {
            log.info("Brands not found on cache, fetching from database...");
            brands = findBrandsByParamsBoundary.execute(brandFilter);
            shouldCacheBrands = true;
        }

        return brands;
    }
}
