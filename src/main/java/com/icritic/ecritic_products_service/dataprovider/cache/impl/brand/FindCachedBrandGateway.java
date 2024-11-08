package com.icritic.ecritic_products_service.dataprovider.cache.impl.brand;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.icritic.ecritic_products_service.core.model.Brand;
import com.icritic.ecritic_products_service.core.usecase.brand.boundary.FindCachedBrandBoundary;
import com.icritic.ecritic_products_service.dataprovider.cache.CacheKeys;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;

import java.util.Optional;

@Component
@RequiredArgsConstructor
@Slf4j
public class FindCachedBrandGateway implements FindCachedBrandBoundary {

    private final Jedis jedis;

    private final ObjectMapper objectMapper;

    public Optional<Brand> execute(Long id) {
        String cacheKey = CacheKeys.BRAND_KEY.buildKey(id.toString());

        log.info("Finding cached brand with key: [{}]", cacheKey);

        try {
            String brandJson = jedis.get(cacheKey);

            if (brandJson == null) {
                return Optional.empty();
            }

            return Optional.of(objectMapper.readValue(brandJson, Brand.class));
        } catch (Exception ex) {
            log.error("Error finding cached brand", ex);
            return Optional.empty();
        }
    }
}
