package com.icritic.ecritic_products_service.dataprovider.cache.impl.brand;

import com.icritic.ecritic_products_service.core.usecase.brand.boundary.FindCachedBrandsCountBoundary;
import com.icritic.ecritic_products_service.dataprovider.cache.CacheKeys;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;

import static java.util.Objects.nonNull;

@Component
@RequiredArgsConstructor
@Slf4j
public class FindCachedBrandsCountGateway implements FindCachedBrandsCountBoundary {

    private final Jedis jedis;

    public Long execute() {
        String cacheKey = CacheKeys.BRANDS_COUNT_KEY.getKey();

        log.info("Finding cached brands count with key: [{}]", cacheKey);

        try {
            String countValue = jedis.get(cacheKey);

            return nonNull(countValue) ? Long.parseLong(countValue) : null;
        } catch (Exception ex) {
            log.error("Error finding cached brands count", ex);
            return null;
        }
    }
}
