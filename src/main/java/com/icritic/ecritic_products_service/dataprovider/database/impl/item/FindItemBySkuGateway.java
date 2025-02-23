package com.icritic.ecritic_products_service.dataprovider.database.impl.item;

import com.icritic.ecritic_products_service.core.model.Item;
import com.icritic.ecritic_products_service.core.usecase.item.boundary.FindItemBySkuBoundary;
import com.icritic.ecritic_products_service.dataprovider.database.entity.ItemEntity;
import com.icritic.ecritic_products_service.dataprovider.database.mapper.ItemEntityMapper;
import com.icritic.ecritic_products_service.dataprovider.database.repository.ItemEntityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class FindItemBySkuGateway implements FindItemBySkuBoundary {

    private final ItemEntityRepository itemEntityRepository;

    private final ItemEntityMapper itemEntityMapper;

    @Override
    public Optional<Item> execute(String sku) {
        ItemEntity itemEntity = itemEntityRepository.findBySku(sku);

        return Optional.ofNullable(itemEntity).map(itemEntityMapper::entityToModel);
    }
}
