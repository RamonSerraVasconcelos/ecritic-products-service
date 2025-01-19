package com.icritic.ecritic_products_service.core.usecase.product.boundary;

import com.icritic.ecritic_products_service.core.model.Product;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface FindCachedProductsBoundary {

    List<Product> execute(Pageable pageable);
}
