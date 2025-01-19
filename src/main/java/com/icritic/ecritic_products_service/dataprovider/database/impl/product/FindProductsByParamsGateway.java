package com.icritic.ecritic_products_service.dataprovider.database.impl.product;

import com.icritic.ecritic_products_service.core.model.Product;
import com.icritic.ecritic_products_service.core.model.ProductFilter;
import com.icritic.ecritic_products_service.core.usecase.product.boundary.FindProductsByParamsBoundary;
import com.icritic.ecritic_products_service.dataprovider.database.entity.ProductEntity;
import com.icritic.ecritic_products_service.dataprovider.database.mapper.ProductEntityMapper;
import com.icritic.ecritic_products_service.dataprovider.database.repository.ProductEntityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class FindProductsByParamsGateway implements FindProductsByParamsBoundary {

    private final ProductEntityRepository productEntityRepository;

    private final ProductEntityMapper productEntityMapper;

    @Override
    public List<Product> execute(ProductFilter productFilter) {
        List<ProductEntity> productsEntities = productEntityRepository.findProductsByParams(productFilter);

        return productsEntities.stream().map(productEntityMapper::entityToModel).toList();
    }
}
