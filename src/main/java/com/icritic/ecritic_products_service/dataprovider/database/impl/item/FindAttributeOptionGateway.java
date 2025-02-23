package com.icritic.ecritic_products_service.dataprovider.database.impl.item;

import com.icritic.ecritic_products_service.core.model.AttributeOption;
import com.icritic.ecritic_products_service.core.model.enums.Attribute;
import com.icritic.ecritic_products_service.core.usecase.item.boundary.FindAttributeOptionBoundary;
import com.icritic.ecritic_products_service.dataprovider.database.entity.AttributeEntity;
import com.icritic.ecritic_products_service.dataprovider.database.entity.AttributeOptionEntity;
import com.icritic.ecritic_products_service.dataprovider.database.mapper.AttributeOptionEntityMapper;
import com.icritic.ecritic_products_service.dataprovider.database.repository.AttributeOptionEntityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class FindAttributeOptionGateway implements FindAttributeOptionBoundary {

    private final AttributeOptionEntityRepository attributeOptionEntityRepository;

    private final AttributeOptionEntityMapper attributeOptionEntityMapper;

    @Override
    public Optional<AttributeOption> execute(Attribute attribute, String value) {
        AttributeOptionEntity attributeOption = attributeOptionEntityRepository.findByAttributeAndValue(AttributeEntity.valueOf(attribute.name()), value);

        return Optional.ofNullable(attributeOption).map(attributeOptionEntityMapper::entityToModel);
    }
}
