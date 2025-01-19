package com.icritic.ecritic_products_service.core.usecase.product.boundary;

import com.icritic.ecritic_products_service.core.model.ProductFilter;

public interface CountProductsBoundary {

    Long execute(ProductFilter productFilter);
}
