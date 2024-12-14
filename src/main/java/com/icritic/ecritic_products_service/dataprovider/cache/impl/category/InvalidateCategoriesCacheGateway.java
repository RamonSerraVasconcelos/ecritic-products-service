package com.icritic.ecritic_products_service.dataprovider.cache.impl.category;

import com.icritic.ecritic_products_service.core.usecase.category.boundary.InvalidateCategoriesCacheBoundary;
import com.icritic.ecritic_products_service.dataprovider.cache.CacheKeys;
import com.icritic.ecritic_products_service.dataprovider.cache.InvalidatePaginationCache;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class InvalidateCategoriesCacheGateway implements InvalidateCategoriesCacheBoundary {

    private final InvalidatePaginationCache invalidatePaginationCache;

    @Override
    public void execute() {
        log.info("Invalidating categories cache");

        try {
            invalidatePaginationCache.execute(CacheKeys.CATEGORIES_KEY);
        } catch (Exception ex) {
            log.error("Error invalidating categories cache", ex);
        }
    }
}
