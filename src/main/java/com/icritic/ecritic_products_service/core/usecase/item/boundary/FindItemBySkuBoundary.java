package com.icritic.ecritic_products_service.core.usecase.item.boundary;

import com.icritic.ecritic_products_service.core.model.Item;

import java.util.Optional;

public interface FindItemBySkuBoundary {

    Optional<Item> execute(String sku);
}
