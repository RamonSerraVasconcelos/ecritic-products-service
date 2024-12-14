package com.icritic.ecritic_products_service.core.usecase.product.boundary;

import com.icritic.ecritic_products_service.core.model.Product;

import java.util.Optional;

public interface FindProductByNameBoundary {

    Optional<Product> execute(String name);
}
