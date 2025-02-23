package com.icritic.ecritic_products_service.core.usecase.item.boundary;

import com.icritic.ecritic_products_service.core.model.Item;

public interface SaveItemBoundary {

    Item execute(Item item);
}
