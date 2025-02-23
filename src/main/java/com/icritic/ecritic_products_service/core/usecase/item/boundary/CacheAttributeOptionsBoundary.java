package com.icritic.ecritic_products_service.core.usecase.item.boundary;

import com.icritic.ecritic_products_service.core.model.AttributeOption;
import org.springframework.data.domain.Page;

public interface CacheAttributeOptionsBoundary {

    void execute(Page<AttributeOption> attributeOptions);
}
