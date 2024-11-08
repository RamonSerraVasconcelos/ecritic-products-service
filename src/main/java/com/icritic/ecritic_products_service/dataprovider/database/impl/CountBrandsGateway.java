package com.icritic.ecritic_products_service.dataprovider.database.impl;

import com.icritic.ecritic_products_service.core.model.BrandFilter;
import com.icritic.ecritic_products_service.core.usecase.brand.boundary.CountBrandsBoundary;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CountBrandsGateway implements CountBrandsBoundary {

    public Long execute(BrandFilter brandFilter) {

    }
}
