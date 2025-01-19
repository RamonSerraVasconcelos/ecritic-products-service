package com.icritic.ecritic_products_service.core.usecase.product.boundary;

import com.icritic.ecritic_products_service.core.model.Product;
import com.icritic.ecritic_products_service.core.model.ProductFilter;

import java.util.List;

public interface FindProductsByParamsBoundary {

    List<Product> execute(ProductFilter productFilter);
}
