package com.icritic.ecritic_products_service.core.usecase.product.boundary;

import com.icritic.ecritic_products_service.core.model.Product;

public interface CacheProductBoundary {

    void execute(Product product);
}
