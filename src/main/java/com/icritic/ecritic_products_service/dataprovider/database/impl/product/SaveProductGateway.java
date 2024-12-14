package com.icritic.ecritic_products_service.dataprovider.database.impl.product;

import com.icritic.ecritic_products_service.core.model.Product;
import com.icritic.ecritic_products_service.core.usecase.product.boundary.SaveProductBoundary;
import com.icritic.ecritic_products_service.dataprovider.database.entity.ProductEntity;
import com.icritic.ecritic_products_service.dataprovider.database.mapper.ProductEntityMapper;
import com.icritic.ecritic_products_service.dataprovider.database.repository.ProductEntityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SaveProductGateway implements SaveProductBoundary {

    private final ProductEntityRepository productEntityRepository;

    private final ProductEntityMapper productEntityMapper;

    @Override
    public Product execute(Product product) {
        ProductEntity productEntity = productEntityRepository.save(productEntityMapper.modelToEntity(product));

        return productEntityMapper.entityToModel(productEntity);
    }
}
