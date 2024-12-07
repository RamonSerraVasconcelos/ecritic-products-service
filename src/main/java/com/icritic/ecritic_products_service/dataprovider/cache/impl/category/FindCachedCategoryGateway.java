package com.icritic.ecritic_products_service.dataprovider.cache.impl.category;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.icritic.ecritic_products_service.core.model.Category;
import com.icritic.ecritic_products_service.core.usecase.category.boundary.FindCachedCategoryBoundary;
import com.icritic.ecritic_products_service.dataprovider.cache.CacheKeys;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;

import java.util.Optional;

@Component
@RequiredArgsConstructor
@Slf4j
public class FindCachedCategoryGateway implements FindCachedCategoryBoundary {

    private final Jedis jedis;

    private final ObjectMapper objectMapper;

    @Override
    public Optional<Category> execute(Long id) {
        String cacheKey = CacheKeys.CATEGORY_KEY.buildKey(id.toString());

        log.info("Finding cached category with key: [{}]", cacheKey);

        try {
            String categoryJson = jedis.get(cacheKey);

            if (categoryJson == null) {
                return Optional.empty();
            }

            return Optional.of(objectMapper.readValue(categoryJson, Category.class));
        } catch (Exception ex) {
            log.error("Error finding cached category", ex);
            return Optional.empty();
        }
    }
}
