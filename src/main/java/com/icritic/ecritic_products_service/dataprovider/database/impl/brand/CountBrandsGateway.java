package com.icritic.ecritic_products_service.dataprovider.database.impl.brand;

import com.icritic.ecritic_products_service.core.model.BrandFilter;
import com.icritic.ecritic_products_service.core.usecase.brand.boundary.CountBrandsBoundary;
import com.icritic.ecritic_products_service.dataprovider.database.repository.BrandEntityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CountBrandsGateway implements CountBrandsBoundary {

    private final BrandEntityRepository brandEntityRepository;

    public Long execute(BrandFilter brandFilter) {
        return brandEntityRepository.countBrandsByParams(brandFilter);
    }
}
