package com.icritic.ecritic_products_service.dataprovider.database.impl.brand;

import com.icritic.ecritic_products_service.core.model.Brand;
import com.icritic.ecritic_products_service.core.model.BrandFilter;
import com.icritic.ecritic_products_service.core.usecase.brand.boundary.FindBrandsByParamsBoundary;
import com.icritic.ecritic_products_service.dataprovider.database.entity.BrandEntity;
import com.icritic.ecritic_products_service.dataprovider.database.mapper.BrandEntityMapper;
import com.icritic.ecritic_products_service.dataprovider.database.repository.BrandEntityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class FindBrandsByParamsGateway implements FindBrandsByParamsBoundary {

    private final BrandEntityRepository brandEntityRepository;

    private final BrandEntityMapper brandEntityMapper;

    @Override
    public List<Brand> execute(BrandFilter brandFilter) {
        List<BrandEntity> brandEntityList = brandEntityRepository.findBrandsByParams(brandFilter);

        return brandEntityList.stream().map(brandEntityMapper::brandEntityToBrand).toList();
    }
}
