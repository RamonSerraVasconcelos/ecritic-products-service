package com.icritic.ecritic_products_service.core.usecase.product.boundary;

import com.icritic.ecritic_products_service.core.model.Product;
import org.springframework.data.domain.Page;

public interface CacheProductsBoundary {

    void execute(Page<Product> products);
}
