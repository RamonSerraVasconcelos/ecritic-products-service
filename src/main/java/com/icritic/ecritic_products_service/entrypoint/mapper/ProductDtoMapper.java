package com.icritic.ecritic_products_service.entrypoint.mapper;

import com.icritic.ecritic_products_service.core.model.Product;
import com.icritic.ecritic_products_service.entrypoint.dto.ProductRequestDto;
import com.icritic.ecritic_products_service.entrypoint.dto.ProductResponseDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ProductDtoMapper {

    @Mapping(source = "brandId", target = "brand.id")
    @Mapping(source = "categoryId", target = "category.id")
    Product productRequestDtoToProduct(ProductRequestDto productRequestDto);

    ProductResponseDto productToProductResponseDto(Product product);
}
