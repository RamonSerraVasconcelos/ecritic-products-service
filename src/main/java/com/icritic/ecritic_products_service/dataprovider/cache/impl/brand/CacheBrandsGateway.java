package com.icritic.ecritic_products_service.dataprovider.cache.impl.brand;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.icritic.ecritic_products_service.config.properties.RedisCacheProperties;
import com.icritic.ecritic_products_service.core.model.Brand;
import com.icritic.ecritic_products_service.core.usecase.brand.boundary.CacheBrandsBoundary;
import com.icritic.ecritic_products_service.dataprovider.cache.CacheKeys;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;

import java.util.concurrent.TimeUnit;

@Component
@RequiredArgsConstructor
@Slf4j
public class CacheBrandsGateway implements CacheBrandsBoundary {

    private final Jedis jedis;

    private final ObjectMapper objectMapper;

    private final RedisCacheProperties redisCacheProperties;

    public void execute(Page<Brand> brands) {
        String cacheKey = CacheKeys.BRANDS_KEY.buildKey(String.valueOf(brands.getNumber()), String.valueOf(brands.getSize()));

        log.info("Caching all brands with key: [{}]", cacheKey);

        try {
            String brandsJson = objectMapper.writeValueAsString(brands.getContent());

            jedis.set(cacheKey, brandsJson);
            jedis.pexpire(cacheKey, TimeUnit.MINUTES.toMillis(redisCacheProperties.getBrandsExpirationTime()));
        } catch (Exception ex) {
            log.error("Error caching all brands", ex);
        }
    }
}
