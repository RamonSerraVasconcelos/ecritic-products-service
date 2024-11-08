package com.icritic.ecritic_products_service.core.usecase.brand.boundary;

import com.icritic.ecritic_products_service.core.model.Brand;
import org.springframework.data.domain.Page;

public interface CacheBrandsBoundary {

    void execute(Page<Brand> brands);
}
