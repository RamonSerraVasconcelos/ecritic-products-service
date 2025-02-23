package com.icritic.ecritic_products_service.dataprovider.database.impl.item;

import com.icritic.ecritic_products_service.core.model.Item;
import com.icritic.ecritic_products_service.core.usecase.item.boundary.SaveItemBoundary;
import com.icritic.ecritic_products_service.dataprovider.database.entity.ItemEntity;
import com.icritic.ecritic_products_service.dataprovider.database.mapper.ItemEntityMapper;
import com.icritic.ecritic_products_service.dataprovider.database.repository.ItemEntityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SaveItemGateway implements SaveItemBoundary {

    private final ItemEntityRepository itemEntityRepository;

    private final ItemEntityMapper itemEntityMapper;

    @Override
    public Item execute(Item item) {
        ItemEntity itemEntity = itemEntityRepository.save(itemEntityMapper.modelToEntity(item));

        return itemEntityMapper.entityToModel(itemEntity);
    }
}
