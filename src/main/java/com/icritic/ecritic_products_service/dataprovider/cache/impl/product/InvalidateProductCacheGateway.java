package com.icritic.ecritic_products_service.dataprovider.cache.impl.product;

import com.icritic.ecritic_products_service.core.usecase.product.boundary.InvalidateProductCacheBoundary;
import com.icritic.ecritic_products_service.dataprovider.cache.CacheKeys;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;

@Component
@RequiredArgsConstructor
@Slf4j
public class InvalidateProductCacheGateway implements InvalidateProductCacheBoundary {

    private final Jedis jedis;

    @Override
    public void execute(Long id) {
        String cacheKey = CacheKeys.PRODUCT_KEY.buildKey(id.toString());

        log.info("Invalidating product with key: [{}]", cacheKey);

        try {
            jedis.del(cacheKey);
        } catch (Exception ex) {
            log.error("Error invalidating product cache", ex);
        }
    }
}
