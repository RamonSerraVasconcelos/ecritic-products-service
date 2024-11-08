package com.icritic.ecritic_products_service.dataprovider.database.impl;

import com.icritic.ecritic_products_service.core.model.Brand;
import com.icritic.ecritic_products_service.core.usecase.brand.boundary.SaveBrandBoundary;
import com.icritic.ecritic_products_service.dataprovider.database.entity.BrandEntity;
import com.icritic.ecritic_products_service.dataprovider.database.mapper.BrandEntityMapper;
import com.icritic.ecritic_products_service.dataprovider.database.repository.BrandEntityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SaveBrandGateway implements SaveBrandBoundary {

    private final BrandEntityMapper brandEntityMapper;

    private final BrandEntityRepository brandEntityRepository;

    public Brand execute(Brand brand) {
        BrandEntity brandEntity = brandEntityMapper.brandToBrandEntity(brand);

        BrandEntity savedBrandEntity = brandEntityRepository.save(brandEntity);

        return brandEntityMapper.brandEntityToBrand(savedBrandEntity);
    }
}
