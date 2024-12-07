package com.icritic.ecritic_products_service.dataprovider.cache.impl.category;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.icritic.ecritic_products_service.config.properties.RedisCacheProperties;
import com.icritic.ecritic_products_service.core.model.Category;
import com.icritic.ecritic_products_service.core.usecase.category.boundary.CacheCategoryBoundary;
import com.icritic.ecritic_products_service.dataprovider.cache.CacheKeys;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;

@Component
@RequiredArgsConstructor
@Slf4j
public class CacheCategoryGateway implements CacheCategoryBoundary {

    private final Jedis jedis;

    private final ObjectMapper objectMapper;

    private final RedisCacheProperties redisCacheProperties;

    @Override
    public void execute(Category category) {
        String cacheKey = CacheKeys.CATEGORY_KEY.buildKey(category.getId().toString());

        log.info("Caching category with key: [{}]", cacheKey);

        try {
            jedis.set(cacheKey, objectMapper.writeValueAsString(category));
            jedis.expire(cacheKey, redisCacheProperties.getCategoryExpirationTime());
        } catch (Exception ex) {
            log.error("Error caching category", ex);
        }
    }
}
