package com.icritic.ecritic_products_service.dataprovider.cache.impl.product;

import com.icritic.ecritic_products_service.core.usecase.product.boundary.InvalidateProductsCacheBoundary;
import com.icritic.ecritic_products_service.dataprovider.cache.CacheKeys;
import com.icritic.ecritic_products_service.dataprovider.cache.InvalidatePaginationCache;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class InvalidateProductsCacheGateway implements InvalidateProductsCacheBoundary {

    private final InvalidatePaginationCache invalidatePaginationCache;

    @Override
    public void execute() {
        log.info("Invalidating products cache");

        try {
            invalidatePaginationCache.execute(CacheKeys.PRODUCTS_KEY);
        } catch (Exception ex) {
            log.error("Error invalidating products cache", ex);
        }
    }
}
