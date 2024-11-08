package com.icritic.ecritic_products_service.core.usecase.brand.boundary;

import com.icritic.ecritic_products_service.core.model.BrandFilter;

public interface CountBrandsBoundary {

    Long execute(BrandFilter brandFilter);
}
