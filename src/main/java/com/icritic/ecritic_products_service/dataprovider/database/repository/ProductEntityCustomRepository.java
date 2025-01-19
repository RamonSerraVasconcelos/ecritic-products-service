package com.icritic.ecritic_products_service.dataprovider.database.repository;

import com.icritic.ecritic_products_service.core.model.ProductFilter;
import com.icritic.ecritic_products_service.dataprovider.database.entity.ProductEntity;

import java.util.List;

public interface ProductEntityCustomRepository {

    List<ProductEntity> findProductsByParams(ProductFilter productFilter);

    Long countProductsByParams(ProductFilter productFilter);
}
