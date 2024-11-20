package com.icritic.ecritic_products_service.dataprovider.cache.impl.brand;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.icritic.ecritic_products_service.config.properties.RedisCacheProperties;
import com.icritic.ecritic_products_service.core.model.Brand;
import com.icritic.ecritic_products_service.core.usecase.brand.boundary.CacheBrandBoundary;
import com.icritic.ecritic_products_service.dataprovider.cache.CacheKeys;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;

import java.util.concurrent.TimeUnit;

@Component
@RequiredArgsConstructor
@Slf4j
public class CacheBrandGateway implements CacheBrandBoundary {

    private final Jedis jedis;

    private final ObjectMapper objectMapper;

    private final RedisCacheProperties redisCacheProperties;

    public void execute(Brand brand) {
        String cacheKey = CacheKeys.BRAND_KEY.buildKey(brand.getId().toString());

        log.info("Caching brand with key: [{}]", cacheKey);

        try {
            String brandJson = objectMapper.writeValueAsString(brand);

            jedis.set(cacheKey, brandJson);
            jedis.pexpire(cacheKey, TimeUnit.MINUTES.toMillis(redisCacheProperties.getBrandExpirationTime()));
        } catch (Exception ex) {
            log.error("Error caching brand", ex);
        }
    }
}
