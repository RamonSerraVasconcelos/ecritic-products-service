package com.icritic.ecritic_products_service.dataprovider.database.mapper;

import com.icritic.ecritic_products_service.core.model.AttributeOption;
import com.icritic.ecritic_products_service.dataprovider.database.entity.AttributeOptionEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AttributeOptionEntityMapper {

    AttributeOptionEntity modelToEntity(AttributeOption attributeOption);

    AttributeOption entityToModel(AttributeOptionEntity attributeOptionEntity);
}
