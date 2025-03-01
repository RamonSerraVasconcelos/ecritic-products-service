package com.icritic.ecritic_products_service.core.usecase.item.boundary;

import com.icritic.ecritic_products_service.core.model.Item;

import java.util.Optional;

public interface FindItemByIdBoundary {

    Optional<Item> execute(Long id);
}
