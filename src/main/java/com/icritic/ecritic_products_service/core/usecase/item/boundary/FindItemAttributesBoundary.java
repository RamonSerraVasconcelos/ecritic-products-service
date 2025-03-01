package com.icritic.ecritic_products_service.core.usecase.item.boundary;

import com.icritic.ecritic_products_service.core.model.ItemAttribute;

import java.util.List;

public interface FindItemAttributesBoundary {

    List<ItemAttribute> execute(Long itemId);
}
