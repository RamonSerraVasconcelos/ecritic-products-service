package com.icritic.ecritic_products_service.dataprovider.database.impl.item;

import com.icritic.ecritic_products_service.core.model.AttributeOption;
import com.icritic.ecritic_products_service.core.usecase.item.boundary.SaveAttributeOptionBoundary;
import com.icritic.ecritic_products_service.dataprovider.database.entity.AttributeOptionEntity;
import com.icritic.ecritic_products_service.dataprovider.database.mapper.AttributeOptionEntityMapper;
import com.icritic.ecritic_products_service.dataprovider.database.repository.AttributeOptionEntityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SaveAttributeOptionGateway implements SaveAttributeOptionBoundary {

    private final AttributeOptionEntityRepository attributeOptionEntityRepository;

    private final AttributeOptionEntityMapper attributeOptionEntityMapper;

    @Override
    public AttributeOption execute(AttributeOption attributeOption) {
        AttributeOptionEntity attributeOptionEntity = attributeOptionEntityMapper.modelToEntity(attributeOption);

        AttributeOptionEntity savedAttributeOption = attributeOptionEntityRepository.save(attributeOptionEntity);

        return attributeOptionEntityMapper.entityToModel(savedAttributeOption);
    }
}
