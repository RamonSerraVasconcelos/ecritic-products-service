package com.icritic.ecritic_products_service.core.usecase.product.boundary;

import com.icritic.ecritic_products_service.core.model.Product;

import java.util.Optional;

public interface FindProductByIdBoundary {

    Optional<Product> execute(Long id);
}
