package com.icritic.ecritic_products_service.core.usecase.category.boundary;

import com.icritic.ecritic_products_service.core.model.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface FindCachedCategoriesBoundary {

    Page<Category> execute(Pageable pageable);
}
