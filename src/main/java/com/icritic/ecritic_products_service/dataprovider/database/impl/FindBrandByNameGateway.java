package com.icritic.ecritic_products_service.dataprovider.database.impl;

import com.icritic.ecritic_products_service.core.model.Brand;
import com.icritic.ecritic_products_service.core.usecase.boundary.FindBrandByNameBoundary;
import com.icritic.ecritic_products_service.dataprovider.database.entity.BrandEntity;
import com.icritic.ecritic_products_service.dataprovider.database.mapper.BrandEntityMapper;
import com.icritic.ecritic_products_service.dataprovider.database.repository.BrandEntityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class FindBrandByNameGateway implements FindBrandByNameBoundary {

    private final BrandEntityMapper brandEntityMapper;

    private final BrandEntityRepository brandEntityRepository;

    public Optional<Brand> execute(String name) {
        BrandEntity brandEntity = brandEntityRepository.findByName(name);

        return Optional.ofNullable(brandEntity).map(brandEntityMapper::brandEntityToBrand);
    }
}
