package com.icritic.ecritic_products_service.dataprovider.database.impl.category;

import com.icritic.ecritic_products_service.core.model.Category;
import com.icritic.ecritic_products_service.core.usecase.category.boundary.SaveCategoryBoundary;
import com.icritic.ecritic_products_service.dataprovider.database.entity.CategoryEntity;
import com.icritic.ecritic_products_service.dataprovider.database.mapper.CategoryEntityMapper;
import com.icritic.ecritic_products_service.dataprovider.database.repository.CategoryEntityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SaveCategoryGateway implements SaveCategoryBoundary {

    private final CategoryEntityRepository categoryEntityRepository;

    private final CategoryEntityMapper categoryEntityMapper;

    @Override
    public Category execute(Category category) {
        CategoryEntity categoryEntity = categoryEntityMapper.categoryToCategoryEntity(category);

        return categoryEntityMapper.categoryEntityToCategory(categoryEntityRepository.save(categoryEntity));
    }
}
