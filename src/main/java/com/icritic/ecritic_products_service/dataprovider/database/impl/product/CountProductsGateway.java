package com.icritic.ecritic_products_service.dataprovider.database.impl.product;

import com.icritic.ecritic_products_service.core.model.ProductFilter;
import com.icritic.ecritic_products_service.core.usecase.product.boundary.CountProductsBoundary;
import com.icritic.ecritic_products_service.dataprovider.database.repository.ProductEntityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CountProductsGateway implements CountProductsBoundary {

    private final ProductEntityRepository productEntityRepository;

    @Override
    public Long execute(ProductFilter productFilter) {
        return productEntityRepository.countProductsByParams(productFilter);
    }
}
