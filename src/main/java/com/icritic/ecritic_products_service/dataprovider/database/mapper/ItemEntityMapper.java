package com.icritic.ecritic_products_service.dataprovider.database.mapper;


import com.icritic.ecritic_products_service.core.model.Item;
import com.icritic.ecritic_products_service.dataprovider.database.entity.ItemEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ItemEntityMapper {

    ItemEntity modelToEntity(Item item);

    Item entityToModel(ItemEntity itemEntity);
}
