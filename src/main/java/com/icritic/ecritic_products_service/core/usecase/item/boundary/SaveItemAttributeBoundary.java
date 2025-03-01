package com.icritic.ecritic_products_service.core.usecase.item.boundary;

import com.icritic.ecritic_products_service.core.model.ItemAttribute;

public interface SaveItemAttributeBoundary {

    ItemAttribute execute(ItemAttribute itemAttribute);
}
