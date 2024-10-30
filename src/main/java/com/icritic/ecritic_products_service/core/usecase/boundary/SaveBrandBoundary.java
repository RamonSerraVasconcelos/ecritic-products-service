package com.icritic.ecritic_products_service.core.usecase.boundary;

import com.icritic.ecritic_products_service.core.model.Brand;

public interface SaveBrandBoundary {

    Brand execute(Brand brand);
}
