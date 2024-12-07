package com.icritic.ecritic_products_service.dataprovider.cache.impl.category;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.icritic.ecritic_products_service.config.properties.RedisCacheProperties;
import com.icritic.ecritic_products_service.core.model.Category;
import com.icritic.ecritic_products_service.core.usecase.category.boundary.CacheCategoriesBoundary;
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
public class CacheCategoriesGateway implements CacheCategoriesBoundary {

    private final Jedis jedis;

    private final ObjectMapper objectMapper;

    private final RedisCacheProperties redisCacheProperties;

    @Override
    public void execute(Page<Category> category) {
        String cacheKey = CacheKeys.CATEGORIES_KEY.buildKey(String.valueOf(category.getNumber()), String.valueOf(category.getSize()));

        log.info("Caching all categories with key: [{}]", cacheKey);

        try {
            String categoriesJson = objectMapper.writeValueAsString(category.getContent());

            jedis.set(cacheKey, categoriesJson);
            jedis.pexpire(cacheKey, TimeUnit.MINUTES.toMillis(redisCacheProperties.getCategoriesExpirationTime()));
        } catch (Exception ex) {
            log.error("Error caching categories", ex);
        }
    }
}
