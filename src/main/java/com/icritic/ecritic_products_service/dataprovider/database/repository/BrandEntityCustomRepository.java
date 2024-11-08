package com.icritic.ecritic_products_service.dataprovider.database.repository;

import com.icritic.ecritic_products_service.core.model.BrandFilter;

public interface BrandEntityCustomRepository {

    Long countBrandsByParams(BrandFilter brandFilter);
}
