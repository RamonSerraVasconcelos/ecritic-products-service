package com.icritic.ecritic_products_service.dataprovider.cache.impl.brand;

import com.icritic.ecritic_products_service.core.usecase.brand.boundary.InvalidateBrandCacheBoundary;
import com.icritic.ecritic_products_service.dataprovider.cache.CacheKeys;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;

@Component
@RequiredArgsConstructor
@Slf4j
public class InvalidateBrandCacheGateway implements InvalidateBrandCacheBoundary {

    private final Jedis jedis;

    @Override
    public void execute(Long id) {
        String cacheKey = CacheKeys.BRAND_KEY.buildKey(id.toString());

        log.info("Invalidating brand with key: [{}]", cacheKey);

        try {
            jedis.del(String.valueOf(id));
        } catch (Exception ex) {
            log.error("Error invalidating brand cache", ex);
        }
    }
}
