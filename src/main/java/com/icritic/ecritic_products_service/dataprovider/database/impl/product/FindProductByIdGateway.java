package com.icritic.ecritic_products_service.dataprovider.database.impl.product;

import com.icritic.ecritic_products_service.core.model.Product;
import com.icritic.ecritic_products_service.core.usecase.product.boundary.FindProductByIdBoundary;
import com.icritic.ecritic_products_service.dataprovider.database.entity.ProductEntity;
import com.icritic.ecritic_products_service.dataprovider.database.mapper.ProductEntityMapper;
import com.icritic.ecritic_products_service.dataprovider.database.repository.ProductEntityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class FindProductByIdGateway implements FindProductByIdBoundary {

    private final ProductEntityRepository productEntityRepository;

    private final ProductEntityMapper productEntityMapper;

    @Override
    public Optional<Product> execute(Long id) {
        ProductEntity productEntity = productEntityRepository.findById(id).orElse(null);

        return Optional.ofNullable(productEntity).map(productEntityMapper::entityToModel);
    }
}
