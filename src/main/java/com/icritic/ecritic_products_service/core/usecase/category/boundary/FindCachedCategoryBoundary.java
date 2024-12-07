package com.icritic.ecritic_products_service.core.usecase.category.boundary;

import com.icritic.ecritic_products_service.core.model.Category;

import java.util.Optional;

public interface FindCachedCategoryBoundary {

    Optional<Category> execute(Long id);
}
