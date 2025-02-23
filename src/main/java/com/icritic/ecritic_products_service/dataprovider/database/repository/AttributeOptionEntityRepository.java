package com.icritic.ecritic_products_service.dataprovider.database.repository;

import com.icritic.ecritic_products_service.dataprovider.database.entity.AttributeEntity;
import com.icritic.ecritic_products_service.dataprovider.database.entity.AttributeOptionEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AttributeOptionEntityRepository extends JpaRepository<AttributeOptionEntity, Long> {

    AttributeOptionEntity findByAttributeAndValue(AttributeEntity attribute, String value);

    Page<AttributeOptionEntity> findByAttribute(Pageable pageable, AttributeEntity attribute);
}
