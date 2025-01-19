package com.icritic.ecritic_products_service.dataprovider.cache.impl.product;

import com.icritic.ecritic_products_service.config.properties.RedisCacheProperties;
import com.icritic.ecritic_products_service.core.usecase.product.boundary.CacheProductsCountBoundary;
import com.icritic.ecritic_products_service.dataprovider.cache.CacheKeys;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;

import java.util.concurrent.TimeUnit;

@Component
@RequiredArgsConstructor
@Slf4j
public class CacheProductsCountGateway implements CacheProductsCountBoundary {

    private final Jedis jedis;

    private final RedisCacheProperties redisCacheProperties;

    @Override
    public void execute(Long count) {
        String cacheKey = CacheKeys.PRODUCTS_COUNT_KEY.getKey();

        log.info("Caching products count with key: [{}]", cacheKey);

        try {
            jedis.set(cacheKey, String.valueOf(count));
            jedis.expire(cacheKey, TimeUnit.MINUTES.toMillis(redisCacheProperties.getProductsCountExpirationTime()));
        } catch (Exception ex) {
            log.error("Error caching products count", ex);
        }
    }
}
