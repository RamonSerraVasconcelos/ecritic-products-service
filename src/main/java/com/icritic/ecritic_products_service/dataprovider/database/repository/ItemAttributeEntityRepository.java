package com.icritic.ecritic_products_service.dataprovider.database.repository;

import com.icritic.ecritic_products_service.dataprovider.database.entity.ItemAttributeEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ItemAttributeEntityRepository extends JpaRepository<ItemAttributeEntity, Long> {

    List<ItemAttributeEntity> findByItemId(Long itemId);
}
