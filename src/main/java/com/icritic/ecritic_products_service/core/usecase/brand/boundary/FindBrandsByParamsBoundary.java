package com.icritic.ecritic_products_service.core.usecase.brand.boundary;

import com.icritic.ecritic_products_service.core.model.Brand;
import com.icritic.ecritic_products_service.core.model.BrandFilter;

import java.util.List;

public interface FindBrandsByParamsBoundary {

    List<Brand> execute(BrandFilter brandsFilter);
}
