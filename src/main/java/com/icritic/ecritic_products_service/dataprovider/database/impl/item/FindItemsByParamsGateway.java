package com.icritic.ecritic_products_service.dataprovider.database.impl.item;

import com.icritic.ecritic_products_service.core.model.Item;
import com.icritic.ecritic_products_service.core.model.ItemFilter;
import com.icritic.ecritic_products_service.core.usecase.item.boundary.FindItemsByParamsBoundary;
import com.icritic.ecritic_products_service.dataprovider.database.entity.ItemEntity;
import com.icritic.ecritic_products_service.dataprovider.database.mapper.ItemEntityMapper;
import com.icritic.ecritic_products_service.dataprovider.database.repository.ItemEntityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class FindItemsByParamsGateway implements FindItemsByParamsBoundary {

    private final ItemEntityRepository itemEntityRepository;

    private final ItemEntityMapper itemEntityMapper;

    @Override
    public List<Item> execute(ItemFilter itemFilter) {
        List<ItemEntity> itemEntities = itemEntityRepository.findItemsByParams(itemFilter);

        return itemEntities.stream().map(itemEntityMapper::entityToModel).toList();
    }
}
