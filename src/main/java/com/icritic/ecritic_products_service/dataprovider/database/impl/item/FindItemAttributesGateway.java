package com.icritic.ecritic_products_service.dataprovider.database.impl.item;

import com.icritic.ecritic_products_service.core.model.ItemAttribute;
import com.icritic.ecritic_products_service.core.usecase.item.boundary.FindItemAttributesBoundary;
import com.icritic.ecritic_products_service.dataprovider.database.entity.ItemAttributeEntity;
import com.icritic.ecritic_products_service.dataprovider.database.mapper.ItemAttributeEntityMapper;
import com.icritic.ecritic_products_service.dataprovider.database.repository.ItemAttributeEntityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class FindItemAttributesGateway implements FindItemAttributesBoundary {

    private final ItemAttributeEntityRepository itemAttributeEntityRepository;

    private final ItemAttributeEntityMapper itemAttributeEntityMapper;

    @Override
    public List<ItemAttribute> execute(Long itemId) {
        List<ItemAttributeEntity> itemAttributeEntities = itemAttributeEntityRepository.findByItemId(itemId);

        return itemAttributeEntities.stream().map(itemAttributeEntityMapper::entityToModel).toList();
    }
}
