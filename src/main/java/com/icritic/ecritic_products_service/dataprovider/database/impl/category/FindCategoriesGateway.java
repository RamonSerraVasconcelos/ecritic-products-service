package com.icritic.ecritic_products_service.dataprovider.database.impl.category;

import com.icritic.ecritic_products_service.core.model.Category;
import com.icritic.ecritic_products_service.core.usecase.category.boundary.FindCategoriesBoundary;
import com.icritic.ecritic_products_service.dataprovider.database.entity.CategoryEntity;
import com.icritic.ecritic_products_service.dataprovider.database.mapper.CategoryEntityMapper;
import com.icritic.ecritic_products_service.dataprovider.database.repository.CategoryEntityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class FindCategoriesGateway implements FindCategoriesBoundary {

    private final CategoryEntityRepository categoryEntityRepository;

    private final CategoryEntityMapper categoryEntityMapper;

    @Override
    public Page<Category> execute(Pageable pageable) {
        Page<CategoryEntity> categoriesEntites = categoryEntityRepository.findAllByOrderByNameAsc(pageable);

        List<Category> categories = categoriesEntites.getContent().stream().map(categoryEntityMapper::categoryEntityToCategory).toList();

        return new PageImpl<>(categories, pageable, categoriesEntites.getTotalElements());
    }
}
