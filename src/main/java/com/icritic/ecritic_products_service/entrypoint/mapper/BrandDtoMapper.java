package com.icritic.ecritic_products_service.entrypoint.mapper;

import com.icritic.ecritic_products_service.core.model.Brand;
import com.icritic.ecritic_products_service.entrypoint.dto.BrandRequestDto;
import com.icritic.ecritic_products_service.entrypoint.dto.BrandResponseDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface BrandDtoMapper {

    Brand brandRequestDtoToBrand(BrandRequestDto brandDto);

    BrandResponseDto brandToBrandResponseDto(Brand brand);
}
