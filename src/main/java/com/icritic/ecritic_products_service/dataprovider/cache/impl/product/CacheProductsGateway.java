package com.icritic.ecritic_products_service.dataprovider.cache.impl.product;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.icritic.ecritic_products_service.config.properties.RedisCacheProperties;
import com.icritic.ecritic_products_service.core.model.Product;
import com.icritic.ecritic_products_service.core.usecase.product.boundary.CacheProductsBoundary;
import com.icritic.ecritic_products_service.dataprovider.cache.CacheKeys;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;

@Component
@RequiredArgsConstructor
@Slf4j
public class CacheProductsGateway implements CacheProductsBoundary {

    private final Jedis jedis;

    private final RedisCacheProperties redisCacheProperties;

    private final ObjectMapper objectMapper;

    @Override
    public void execute(Page<Product> products) {
        String cacheKey = CacheKeys.PRODUCTS_KEY.buildKey(String.valueOf(products.getNumber()), String.valueOf(products.getSize()));

        log.info("Caching all products with key: [{}]", cacheKey);

        try {
            String productsJson = objectMapper.writeValueAsString(products.getContent());

            jedis.set(cacheKey, productsJson);
            jedis.expire(cacheKey, redisCacheProperties.getProductsExpirationTime());
        } catch (Exception ex) {
            log.error("Error caching all products", ex);
        }
    }
}
