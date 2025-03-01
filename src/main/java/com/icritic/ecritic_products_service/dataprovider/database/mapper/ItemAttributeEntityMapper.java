package com.icritic.ecritic_products_service.dataprovider.database.mapper;

import com.icritic.ecritic_products_service.core.model.ItemAttribute;
import com.icritic.ecritic_products_service.dataprovider.database.entity.ItemAttributeEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ItemAttributeEntityMapper {

    ItemAttributeEntity modelToEntity(ItemAttribute itemAttribute);

    ItemAttribute entityToModel(ItemAttributeEntity itemAttributeEntity);
}
