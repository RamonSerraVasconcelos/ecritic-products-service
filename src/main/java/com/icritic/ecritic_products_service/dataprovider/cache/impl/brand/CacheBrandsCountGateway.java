package com.icritic.ecritic_products_service.dataprovider.cache.impl.brand;

import com.icritic.ecritic_products_service.config.properties.RedisCacheProperties;
import com.icritic.ecritic_products_service.core.usecase.brand.boundary.CacheBrandsCountBoundary;
import com.icritic.ecritic_products_service.dataprovider.cache.CacheKeys;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;

import java.util.concurrent.TimeUnit;

@Component
@RequiredArgsConstructor
@Slf4j
public class CacheBrandsCountGateway implements CacheBrandsCountBoundary {

    private final Jedis jedis;

    private final RedisCacheProperties redisCacheProperties;

    public void execute(Long count) {
        String cacheKey = CacheKeys.BRANDS_COUNT_KEY.getKey();

        log.info("Caching brands count with key: [{}]", cacheKey);

        try {
            jedis.set(cacheKey, String.valueOf(count));
            jedis.pexpire(cacheKey, TimeUnit.MINUTES.toMillis(redisCacheProperties.getBrandsCountExpirationTime()));
        } catch (Exception ex) {
            log.error("Error caching brands count", ex);
        }
    }
}
