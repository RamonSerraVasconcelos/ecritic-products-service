package com.icritic.ecritic_products_service.dataprovider.database.impl.product;

import com.icritic.ecritic_products_service.core.model.Product;
import com.icritic.ecritic_products_service.core.usecase.product.boundary.FindProductByNameBoundary;
import com.icritic.ecritic_products_service.dataprovider.database.entity.ProductEntity;
import com.icritic.ecritic_products_service.dataprovider.database.mapper.ProductEntityMapper;
import com.icritic.ecritic_products_service.dataprovider.database.repository.ProductEntityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class FindProductByNameGateway implements FindProductByNameBoundary {

    private final ProductEntityRepository productRepository;

    private final ProductEntityMapper productEntityMapper;

    @Override
    public Optional<Product> execute(String name) {
        ProductEntity productEntity = productRepository.findByName(name);

        return Optional.ofNullable(productEntity).map(productEntityMapper::entityToModel);
    }
}
