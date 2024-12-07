package com.icritic.ecritic_products_service.dataprovider.cache;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum CacheKeys {

    BRANDS_KEY("brands:%s:%s"),
    BRANDS_COUNT_KEY("brands-count"),
    BRAND_KEY("brand:%s"),
    CATEGORIES_KEY("categories:%s:%s"),
    CATEGORY_KEY("category:%s");

    private final String key;

    public String buildKey(String... replacements) {
        return String.format(key, (Object[]) replacements);
    }
}
