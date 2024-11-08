package com.icritic.ecritic_products_service.core.usecase.brand.boundary;

import com.icritic.ecritic_products_service.core.model.Brand;

import java.util.Optional;

public interface FindBrandByNameBoundary {

    Optional<Brand> execute(String name);
}
