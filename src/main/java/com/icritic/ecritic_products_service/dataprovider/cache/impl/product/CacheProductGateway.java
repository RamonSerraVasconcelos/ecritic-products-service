package com.icritic.ecritic_products_service.dataprovider.cache.impl.product;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.icritic.ecritic_products_service.config.properties.RedisCacheProperties;
import com.icritic.ecritic_products_service.core.model.Product;
import com.icritic.ecritic_products_service.core.usecase.product.boundary.CacheProductBoundary;
import com.icritic.ecritic_products_service.dataprovider.cache.CacheKeys;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;

@Component
@RequiredArgsConstructor
@Slf4j
public class CacheProductGateway implements CacheProductBoundary {

    private final Jedis jedis;

    private final ObjectMapper objectMapper;

    private final RedisCacheProperties redisCacheProperties;

    @Override
    public void execute(Product product) {
        String cacheKey = CacheKeys.PRODUCT_KEY.buildKey(product.getId().toString());

        log.info("Caching product with key: [{}]", cacheKey);

        try {
            jedis.set(cacheKey, objectMapper.writeValueAsString(product));
            jedis.expire(cacheKey, redisCacheProperties.getProductExpirationTime());
        } catch (Exception ex) {
            log.error("Error caching product", ex);
        }
    }
}
