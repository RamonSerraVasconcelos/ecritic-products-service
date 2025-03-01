package com.icritic.ecritic_products_service.dataprovider.database.impl.item;

import com.icritic.ecritic_products_service.core.model.ItemAttribute;
import com.icritic.ecritic_products_service.core.usecase.item.boundary.SaveItemAttributeBoundary;
import com.icritic.ecritic_products_service.dataprovider.database.entity.ItemAttributeEntity;
import com.icritic.ecritic_products_service.dataprovider.database.mapper.ItemAttributeEntityMapper;
import com.icritic.ecritic_products_service.dataprovider.database.repository.ItemAttributeEntityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SaveItemAttributeGateway implements SaveItemAttributeBoundary {

    private final ItemAttributeEntityRepository itemAttributeEntityRepository;

    private final ItemAttributeEntityMapper itemAttributeEntityMapper;

    public ItemAttribute execute(ItemAttribute itemAttribute) {
        ItemAttributeEntity itemAttributeEntity = itemAttributeEntityRepository.save(itemAttributeEntityMapper.modelToEntity(itemAttribute));

        return itemAttributeEntityMapper.entityToModel(itemAttributeEntity);
    }
}
