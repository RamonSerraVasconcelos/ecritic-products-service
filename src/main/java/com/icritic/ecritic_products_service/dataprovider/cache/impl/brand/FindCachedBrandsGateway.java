package com.icritic.ecritic_products_service.dataprovider.cache.impl.brand;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.icritic.ecritic_products_service.core.model.Brand;
import com.icritic.ecritic_products_service.core.usecase.brand.boundary.FindCachedBrandsBoundary;
import com.icritic.ecritic_products_service.dataprovider.cache.CacheKeys;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;

import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class FindCachedBrandsGateway implements FindCachedBrandsBoundary {

    private final Jedis jedis;

    private final ObjectMapper objectMapper;

    public List<Brand> execute(Pageable pageable) {
        String cacheKey = CacheKeys.BRANDS_KEY.buildKey(String.valueOf(pageable.getPageNumber()), String.valueOf(pageable.getPageSize()));

        log.info("Retrieving cached brands with key: [{}]", cacheKey);

        try {
            String brandsJson = jedis.get(cacheKey);

            if (brandsJson == null) {
                return List.of();
            }

            return objectMapper.readValue(brandsJson, new TypeReference<>() {});
        } catch (Exception ex) {
            log.error("Error retrieving cached brands", ex);
            return List.of();
        }
    }
}
