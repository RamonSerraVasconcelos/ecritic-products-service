package com.icritic.ecritic_products_service.core.usecase.item.boundary;

import com.icritic.ecritic_products_service.core.model.AttributeOption;

import java.util.Optional;

public interface FindAttributeOptionByIdBoundary {

    Optional<AttributeOption> execute(Long id);
}
