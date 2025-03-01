package com.icritic.ecritic_products_service.dataprovider.database.impl.item;

import com.icritic.ecritic_products_service.core.model.ItemFilter;
import com.icritic.ecritic_products_service.core.usecase.item.boundary.CountItemsByProductBoundary;
import com.icritic.ecritic_products_service.dataprovider.database.repository.ItemEntityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CountItemsByProductGateway implements CountItemsByProductBoundary {

    private final ItemEntityRepository itemEntityRepository;

    public Long execute(ItemFilter itemFilter) {
        return itemEntityRepository.countItemsByParams(itemFilter);
    }
}
