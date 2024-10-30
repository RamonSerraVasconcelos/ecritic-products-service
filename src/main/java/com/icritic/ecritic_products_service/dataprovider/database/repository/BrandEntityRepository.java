package com.icritic.ecritic_products_service.dataprovider.database.repository;

import com.icritic.ecritic_products_service.dataprovider.database.entity.BrandEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BrandEntityRepository extends JpaRepository<BrandEntity, Long> {

    BrandEntity findByName(String name);
}
