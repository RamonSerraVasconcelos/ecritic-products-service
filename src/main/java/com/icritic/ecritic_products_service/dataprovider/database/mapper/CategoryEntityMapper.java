package com.icritic.ecritic_products_service.dataprovider.database.mapper;

import com.icritic.ecritic_products_service.core.model.Category;
import com.icritic.ecritic_products_service.dataprovider.database.entity.CategoryEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CategoryEntityMapper {

    CategoryEntity categoryToCategoryEntity(Category category);

    Category categoryEntityToCategory(CategoryEntity categoryEntity);
}
