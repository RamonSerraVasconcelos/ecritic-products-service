package com.icritic.ecritic_products_service.dataprovider.cache.impl.product;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.icritic.ecritic_products_service.core.model.Product;
import com.icritic.ecritic_products_service.core.usecase.product.boundary.FindCachedProductBoundary;
import com.icritic.ecritic_products_service.dataprovider.cache.CacheKeys;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;

import java.util.Optional;

@Component
@RequiredArgsConstructor
@Slf4j
public class FindCachedProductGateway implements FindCachedProductBoundary {

    private final Jedis jedis;

    private final ObjectMapper objectMapper;

    @Override
    public Optional<Product> execute(Long id) {
        String cacheKey = CacheKeys.PRODUCT_KEY.buildKey(id.toString());

        log.info("Finding cached product with key: [{}]", cacheKey);

        try {
            String productJson = jedis.get(cacheKey);

            if (productJson == null) {
                return Optional.empty();
            }

            return Optional.of(objectMapper.readValue(productJson, Product.class));
        } catch (Exception ex) {
            log.error("Error finding cached product", ex);
            return Optional.empty();
        }
    }
}
