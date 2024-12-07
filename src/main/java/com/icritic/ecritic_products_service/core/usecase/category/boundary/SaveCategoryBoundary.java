package com.icritic.ecritic_products_service.core.usecase.category.boundary;

import com.icritic.ecritic_products_service.core.model.Category;

public interface SaveCategoryBoundary {

    Category execute(Category category);
}
