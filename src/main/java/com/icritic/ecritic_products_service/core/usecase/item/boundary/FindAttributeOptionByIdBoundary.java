package com.icritic.ecritic_products_service.core.usecase.item.boundary;

import com.icritic.ecritic_products_service.core.model.AttributeOption;

public interface FindAttributeOptionByIdBoundary {

    AttributeOption execute(Long id);
}
