package com.icritic.ecritic_products_service.dataprovider.database.repository;

import com.icritic.ecritic_products_service.dataprovider.database.entity.CategoryEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;


public interface CategoryEntityRepository extends JpaRepository<CategoryEntity, Long> {

    CategoryEntity findByName(String name);

    Page<CategoryEntity> findAllByOrderByNameAsc(Pageable pageable);
}
