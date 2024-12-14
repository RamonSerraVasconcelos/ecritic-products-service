package com.icritic.ecritic_products_service.dataprovider.database.mapper;

import com.icritic.ecritic_products_service.core.model.Product;
import com.icritic.ecritic_products_service.dataprovider.database.entity.ProductEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ProductEntityMapper {

    ProductEntity modelToEntity(Product product);

    Product entityToModel(ProductEntity productEntity);
}
