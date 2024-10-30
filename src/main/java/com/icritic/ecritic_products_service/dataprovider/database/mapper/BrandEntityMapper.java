package com.icritic.ecritic_products_service.dataprovider.database.mapper;

import com.icritic.ecritic_products_service.core.model.Brand;
import com.icritic.ecritic_products_service.dataprovider.database.entity.BrandEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface BrandEntityMapper {

    BrandEntity brandToBrandEntity(Brand brand);

    Brand brandEntityToBrand(BrandEntity brandEntity);
}
