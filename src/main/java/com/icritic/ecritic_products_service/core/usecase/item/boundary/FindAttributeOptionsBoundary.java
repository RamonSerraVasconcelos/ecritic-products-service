package com.icritic.ecritic_products_service.core.usecase.item.boundary;

import com.icritic.ecritic_products_service.core.model.AttributeOption;
import com.icritic.ecritic_products_service.core.model.enums.Attribute;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface FindAttributeOptionsBoundary {

    Page<AttributeOption> execute(Pageable pageable, Attribute attribute);
}
