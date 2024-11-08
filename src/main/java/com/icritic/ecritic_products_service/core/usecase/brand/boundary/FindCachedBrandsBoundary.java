package com.icritic.ecritic_products_service.core.usecase.brand.boundary;

import com.icritic.ecritic_products_service.core.model.Brand;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface FindCachedBrandsBoundary {

    List<Brand> execute(Pageable pageable);
}
