package com.icritic.ecritic_products_service.dataprovider.database.repository;

import com.icritic.ecritic_products_service.core.model.ItemFilter;
import com.icritic.ecritic_products_service.dataprovider.database.entity.ItemEntity;

import java.util.List;

public interface ItemEntityCustomRepository {

    List<ItemEntity> findItemsByParams(ItemFilter itemFilter);

    Long countItemsByParams(ItemFilter itemFilter);
}
