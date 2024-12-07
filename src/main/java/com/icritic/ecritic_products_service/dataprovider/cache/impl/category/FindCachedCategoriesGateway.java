package com.icritic.ecritic_products_service.dataprovider.cache.impl.category;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.icritic.ecritic_products_service.core.model.Category;
import com.icritic.ecritic_products_service.core.usecase.category.boundary.FindCachedCategoriesBoundary;
import com.icritic.ecritic_products_service.dataprovider.cache.CacheKeys;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;

import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class FindCachedCategoriesGateway implements FindCachedCategoriesBoundary {

    private final Jedis jedis;

    private final ObjectMapper objectMapper;

    @Override
    public Page<Category> execute(Pageable pageable) {
        String cacheKey = CacheKeys.CATEGORIES_KEY.buildKey(String.valueOf(pageable.getPageNumber()), String.valueOf(pageable.getPageSize()));

        log.info("Fetching cached categories with key: [{}]", cacheKey);

        try {
            String categoriesJson = jedis.get(cacheKey);

            if (categoriesJson == null) {
                return null;
            }

            List<Category> categoryList = objectMapper.readValue(categoriesJson, new TypeReference<>() {});

            return new PageImpl<>(categoryList, pageable, categoryList.size());
        } catch (Exception ex) {
            log.error("Error fetching cached categories", ex);
            return null;
        }
    }
}
