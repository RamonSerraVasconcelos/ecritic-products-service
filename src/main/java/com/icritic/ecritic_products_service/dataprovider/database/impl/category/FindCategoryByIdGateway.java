package com.icritic.ecritic_products_service.dataprovider.database.impl.category;

import com.icritic.ecritic_products_service.core.model.Category;
import com.icritic.ecritic_products_service.core.usecase.category.boundary.FindCategoryByIdBoundary;
import com.icritic.ecritic_products_service.dataprovider.database.entity.CategoryEntity;
import com.icritic.ecritic_products_service.dataprovider.database.mapper.CategoryEntityMapper;
import com.icritic.ecritic_products_service.dataprovider.database.repository.CategoryEntityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class FindCategoryByIdGateway implements FindCategoryByIdBoundary {

    private final CategoryEntityRepository categoryEntityRepository;

    private final CategoryEntityMapper categoryEntityMapper;

    @Override
    public Optional<Category> execute(Long id) {
        CategoryEntity categoryEntity = categoryEntityRepository.findById(id).orElse(null);

        return Optional.ofNullable(categoryEntity).map(categoryEntityMapper::categoryEntityToCategory);
    }
}
