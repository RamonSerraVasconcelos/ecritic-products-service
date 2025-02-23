package com.icritic.ecritic_products_service.dataprovider.database.impl.item;

import com.icritic.ecritic_products_service.core.model.AttributeOption;
import com.icritic.ecritic_products_service.core.usecase.item.boundary.FindAttributeOptionByIdBoundary;
import com.icritic.ecritic_products_service.dataprovider.database.repository.AttributeOptionEntityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class FindAttributeOptionByIdGateway implements FindAttributeOptionByIdBoundary {

    private final AttributeOptionEntityRepository attributeOptionEntityRepository;

    @Override
    public Optional<AttributeOption> execute(Long id) {

    }
}
