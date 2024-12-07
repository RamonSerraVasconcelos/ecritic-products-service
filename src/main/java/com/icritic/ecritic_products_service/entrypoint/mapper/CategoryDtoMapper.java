package com.icritic.ecritic_products_service.entrypoint.mapper;

import com.icritic.ecritic_products_service.core.model.Category;
import com.icritic.ecritic_products_service.entrypoint.dto.CategoryRequestDto;
import com.icritic.ecritic_products_service.entrypoint.dto.CategoryResponseDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CategoryDtoMapper {

    Category categoryRequestDtoToCategory(CategoryRequestDto categoryRequestDto);

    CategoryResponseDto categoryToCategoryResponseDto(Category category);
}
