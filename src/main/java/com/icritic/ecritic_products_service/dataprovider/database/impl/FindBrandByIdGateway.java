package com.icritic.ecritic_products_service.dataprovider.database.impl;

import com.icritic.ecritic_products_service.core.model.Brand;
import com.icritic.ecritic_products_service.core.usecase.boundary.FindBrandByIdBoundary;
import com.icritic.ecritic_products_service.dataprovider.database.mapper.BrandEntityMapper;
import com.icritic.ecritic_products_service.dataprovider.database.repository.BrandEntityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class FindBrandByIdGateway implements FindBrandByIdBoundary {

    private final BrandEntityMapper brandEntityMapper;

    private final BrandEntityRepository brandEntityRepository;

    public Optional<Brand> execute(Long id) {
        return brandEntityRepository
                .findById(id)
                .map(brandEntityMapper::brandEntityToBrand);
    }
}
