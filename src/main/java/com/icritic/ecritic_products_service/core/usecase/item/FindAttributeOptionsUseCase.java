package com.icritic.ecritic_products_service.core.usecase.item;

import com.icritic.ecritic_products_service.core.model.AttributeOption;
import com.icritic.ecritic_products_service.core.model.enums.Attribute;
import com.icritic.ecritic_products_service.core.usecase.item.boundary.FindAttributeOptionsBoundary;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class FindAttributeOptionsUseCase {

    private FindAttributeOptionsBoundary findAttributeOptionsBoundary;

    public Page<AttributeOption> execute(Pageable pageable, Attribute attribute) {
        log.info("Finding attribute options for attribute: [{}]", attribute);

        return findAttributeOptionsBoundary.execute(pageable, attribute);
    }
}
