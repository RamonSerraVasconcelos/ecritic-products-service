package com.icritic.ecritic_products_service.dataprovider.database.impl.item;

import com.icritic.ecritic_products_service.core.model.Item;
import com.icritic.ecritic_products_service.core.usecase.item.boundary.FindItemByIdBoundary;
import com.icritic.ecritic_products_service.dataprovider.database.entity.ItemEntity;
import com.icritic.ecritic_products_service.dataprovider.database.mapper.ItemEntityMapper;
import com.icritic.ecritic_products_service.dataprovider.database.repository.ItemEntityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class FindItemByIdGateway implements FindItemByIdBoundary {

    private final ItemEntityRepository itemEntityRepository;

    private final ItemEntityMapper itemEntityMapper;

    public Optional<Item> execute(Long id) {
        ItemEntity itemEntity = itemEntityRepository.findById(id).orElse(null);

        return Optional.ofNullable(itemEntity).map(itemEntityMapper::entityToModel);
    }
}
