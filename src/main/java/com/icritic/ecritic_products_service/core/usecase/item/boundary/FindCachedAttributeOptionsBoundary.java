package com.icritic.ecritic_products_service.core.usecase.item.boundary;

import com.icritic.ecritic_products_service.core.model.AttributeOption;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface FindCachedAttributeOptionsBoundary {

    List<AttributeOption> execute(Pageable pageable);
}
