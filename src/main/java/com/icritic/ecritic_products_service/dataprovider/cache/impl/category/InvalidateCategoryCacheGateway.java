package com.icritic.ecritic_products_service.dataprovider.cache.impl.category;

import com.icritic.ecritic_products_service.core.usecase.category.boundary.InvalidateCategoryCacheBoundary;
import com.icritic.ecritic_products_service.dataprovider.cache.CacheKeys;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;

@Component
@RequiredArgsConstructor
@Slf4j
public class InvalidateCategoryCacheGateway implements InvalidateCategoryCacheBoundary {

    private final Jedis jedis;

    @Override
    public void execute(Long id) {
        String cacheKey = CacheKeys.CATEGORY_KEY.buildKey(id.toString());

        log.info("Invalidating category with key: [{}]", cacheKey);

        try {
            jedis.del(cacheKey);
        } catch (Exception ex) {
            log.error("Error invalidating category", ex);
        }
    }
}
