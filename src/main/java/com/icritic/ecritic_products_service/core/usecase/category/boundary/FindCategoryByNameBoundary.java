package com.icritic.ecritic_products_service.core.usecase.category.boundary;

import com.icritic.ecritic_products_service.core.model.Category;

import java.util.Optional;

public interface FindCategoryByNameBoundary {

    Optional<Category> execute(String name);
}
