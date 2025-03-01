package com.icritic.ecritic_products_service.core.usecase.item.boundary;

import com.icritic.ecritic_products_service.core.model.ItemFilter;

public interface CountItemsByProductBoundary {

    Long execute(ItemFilter itemFilter);
}
