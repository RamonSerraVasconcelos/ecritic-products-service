package com.icritic.ecritic_products_service.core.usecase.product;

import com.icritic.ecritic_products_service.core.model.ProductFilter;
import com.icritic.ecritic_products_service.core.usecase.brand.boundary.CountBrandsBoundary;
import com.icritic.ecritic_products_service.core.usecase.product.boundary.CacheProductsCountBoundary;
import com.icritic.ecritic_products_service.core.usecase.product.boundary.CountProductsBoundary;
import com.icritic.ecritic_products_service.core.usecase.product.boundary.FindCachedProductsBoundary;
import com.icritic.ecritic_products_service.core.usecase.product.boundary.FindCachedProductsCountBoundary;
import com.icritic.ecritic_products_service.exception.DefaultException;
import com.icritic.ecritic_products_service.exception.InternalErrorException;
import com.icritic.ecritic_products_service.exception.handler.ErrorResponseCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class CountProductsUseCase {

    private final FindCachedProductsCountBoundary findCachedProductsCountBoundary;

    private final CountProductsBoundary countProductsBoundary;

    private final CacheProductsCountBoundary cacheProductsCountBoundary;

    public long execute(ProductFilter productFilter) {
        log.info("Counting all products");

        try {
            if (productFilter.isCacheable()) {
                Long count = findCachedProductsCountBoundary.execute();

                if (count != null) {
                    return count;
                }
            }

            Long count = countProductsBoundary.execute(productFilter);

            if (productFilter.isCacheable()) cacheProductsCountBoundary.execute(count);

            return count;
        } catch (DefaultException ex) {
            log.error("Error counting products. Exception: [{}]", ex.getErrorResponse());
            throw ex;
        } catch (Exception ex) {
            log.error("Error counting products", ex);
            throw new InternalErrorException(ErrorResponseCode.ECRITICPROD_09);
        }
    }
}
