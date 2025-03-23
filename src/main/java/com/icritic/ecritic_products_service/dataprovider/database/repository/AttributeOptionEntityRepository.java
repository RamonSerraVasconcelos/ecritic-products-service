package com.icritic.ecritic_products_service.dataprovider.database.repository;

import com.icritic.ecritic_products_service.dataprovider.database.entity.AttributeOptionEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface AttributeOptionEntityRepository extends JpaRepository<AttributeOptionEntity, Long>, AttributeOptionEntityCustomRepository {

    @Query(value = "SELECT id, attribute, value FROM attribute_options WHERE attribute = CAST(:attribute AS attributes) AND value = :value", nativeQuery = true)
    AttributeOptionEntity findByAttributeAndValue(@Param("attribute") String attribute, @Param("value") String value);

    @Query(value = "SELECT * FROM attribute_options WHERE attribute = CAST(:attribute AS attributes)", nativeQuery = true)
    Page<AttributeOptionEntity> findByAttribute(Pageable pageable, @Param("attribute") String attribute);
}
