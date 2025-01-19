package com.icritic.ecritic_products_service.dataprovider.cache.impl.product;

import com.icritic.ecritic_products_service.core.usecase.product.boundary.FindCachedProductsCountBoundary;
import com.icritic.ecritic_products_service.dataprovider.cache.CacheKeys;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;

import static java.util.Objects.nonNull;

@Component
@RequiredArgsConstructor
@Slf4j
public class FindCachedProductsCountGateway implements FindCachedProductsCountBoundary {

    private final Jedis jedis;

    @Override
    public Long execute() {
        String cacheKey = CacheKeys.PRODUCTS_COUNT_KEY.getKey();

        log.info("Finding cached products count");

        try {
            String countValue = jedis.get(cacheKey);

            return nonNull(countValue) ? Long.parseLong(countValue) : null;
        } catch (Exception ex) {
            log.error("Error finding cached products count", ex);
            return null;
        }
    }
}
