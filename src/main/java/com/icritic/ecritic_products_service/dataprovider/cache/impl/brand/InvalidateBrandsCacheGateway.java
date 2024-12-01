package com.icritic.ecritic_products_service.dataprovider.cache.impl.brand;

import com.icritic.ecritic_products_service.core.usecase.brand.boundary.InvalidateBrandsCacheBoundary;
import com.icritic.ecritic_products_service.dataprovider.cache.CacheKeys;
import com.icritic.ecritic_products_service.dataprovider.cache.InvalidatePaginationCache;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class InvalidateBrandsCacheGateway implements InvalidateBrandsCacheBoundary {

    private final InvalidatePaginationCache invalidatePaginationCache;

    @Override
    public void execute() {
        try {
            invalidatePaginationCache.execute(CacheKeys.BRANDS_KEY.getKey());
        } catch (Exception ex) {
            log.error("Error invalidating brands cache", ex);
        }
    }
}
