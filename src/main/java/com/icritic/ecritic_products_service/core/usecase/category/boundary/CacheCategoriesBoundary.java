package com.icritic.ecritic_products_service.core.usecase.category.boundary;

import com.icritic.ecritic_products_service.core.model.Category;
import org.springframework.data.domain.Page;

public interface CacheCategoriesBoundary {

    void execute(Page<Category> categories);
}
