package com.icritic.ecritic_products_service.core.usecase.item.boundary;

import com.icritic.ecritic_products_service.core.model.Item;
import com.icritic.ecritic_products_service.core.model.ItemFilter;

import java.util.List;

public interface FindItemsByParamsBoundary {

    List<Item> execute(ItemFilter itemFilter);
}
