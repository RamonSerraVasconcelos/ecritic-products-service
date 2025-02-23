package com.icritic.ecritic_products_service.core.usecase.item.boundary;

import com.icritic.ecritic_products_service.core.model.AttributeOption;
import com.icritic.ecritic_products_service.core.model.enums.Attribute;

import java.util.Optional;

public interface FindAttributeOptionBoundary {

    Optional<AttributeOption> execute(Attribute attribute, String value);
}
