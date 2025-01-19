package com.icritic.ecritic_products_service.dataprovider.cache.impl.product;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.icritic.ecritic_products_service.core.model.Product;
import com.icritic.ecritic_products_service.core.usecase.product.boundary.FindCachedProductsBoundary;
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
public class FindCachedProductsGateway implements FindCachedProductsBoundary {

    private final Jedis jedis;

    private final ObjectMapper objectMapper;

    @Override
    public List<Product> execute(Pageable pageable) {
        String cacheKey = CacheKeys.PRODUCTS_KEY.buildKey(String.valueOf(pageable.getPageNumber()), String.valueOf(pageable.getPageSize()));

        log.info("Fetching cached products with key: [{}]", cacheKey);

        try {
            String productsJson = jedis.get(cacheKey);

            if (productsJson == null) {
                return List.of();
            }

            return objectMapper.readValue(productsJson, new TypeReference<>() {});
        } catch (Exception ex) {
            log.error("Error fetching cached products", ex);
            return List.of();
        }
    }
}
