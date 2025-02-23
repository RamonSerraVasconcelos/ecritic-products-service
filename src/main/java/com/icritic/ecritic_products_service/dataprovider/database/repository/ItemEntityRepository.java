package com.icritic.ecritic_products_service.dataprovider.database.repository;

import com.icritic.ecritic_products_service.dataprovider.database.entity.ItemEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemEntityRepository extends JpaRepository<ItemEntity, Long> {

    ItemEntity findBySku(String sku);
}
