package com.icritic.ecritic_products_service.dataprovider.database.repository;

import com.icritic.ecritic_products_service.core.model.BrandFilter;
import com.icritic.ecritic_products_service.dataprovider.database.entity.BrandEntity;

import java.util.List;

public interface BrandEntityCustomRepository {

    List<BrandEntity> findBrandsByParams(BrandFilter brandFilter);

    Long countBrandsByParams(BrandFilter brandFilter);
}
