package com.icritic.ecritic_products_service.dataprovider.database.repository.impl;

import com.icritic.ecritic_products_service.core.model.ProductFilter;
import com.icritic.ecritic_products_service.dataprovider.database.entity.ProductEntity;
import com.icritic.ecritic_products_service.dataprovider.database.repository.ProductEntityCustomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import java.util.List;

import static java.util.Objects.nonNull;

@Repository
@RequiredArgsConstructor
public class ProductEntityCustomRepositoryImpl implements ProductEntityCustomRepository {

    private final NamedParameterJdbcTemplate jdbcTemplate;

    private static final String SELECT = "SELECT p.id, p.name, p.category_id, p.brand_id, p.description, p.active FROM products p";

    private static final String SELECT_COUNT = "SELECT COUNT(p.id) FROM products p";

    @Override
    public List<ProductEntity> findProductsByParams(ProductFilter productFilter) {
        StringBuilder queryBuilder = new StringBuilder(SELECT);
        MapSqlParameterSource params = new MapSqlParameterSource();

        addConditions(productFilter, queryBuilder, params);

        String query = getFinalQuery(productFilter, queryBuilder, params);

        return jdbcTemplate.query(query, params, new BeanPropertyRowMapper<>(ProductEntity.class));
    }

    @Override
    public Long countProductsByParams(ProductFilter productFilter) {
        StringBuilder queryBuilder = new StringBuilder(SELECT_COUNT);
        MapSqlParameterSource params = new MapSqlParameterSource();

        addConditions(productFilter, queryBuilder, params);

        return jdbcTemplate.queryForObject(queryBuilder.toString(), params, Long.class);
    }

    private void addConditions(ProductFilter productFilter, StringBuilder queryBuilder, MapSqlParameterSource params) {
        queryBuilder.append(" WHERE p.active = :active");
        params.addValue("active", productFilter.isActive());

        if (nonNull(productFilter.getBrands()) && !productFilter.getBrands().isEmpty()) {
            queryBuilder.append(" AND p.brand_id IN (:brandIds)");
            params.addValue("brandIds", productFilter.getBrands());
        }

        if (nonNull(productFilter.getCategories()) && !productFilter.getCategories().isEmpty()) {
            queryBuilder.append(" AND p.category_id IN (:categoryIds)");
            params.addValue("categoryIds", productFilter.getCategories());
        }

        if (StringUtils.hasText(productFilter.getName())) {
            String name = productFilter.getName();
            String trimmedName = name.substring(0, name.lastIndexOf(name.trim()) + name.trim().length()).replace(" ", " & ");

            queryBuilder.append(" AND to_tsvector(p.name) @@ to_tsquery(:name)");
            params.addValue("name", trimmedName);
        }
    }

    private String getFinalQuery(ProductFilter productFilter, StringBuilder queryBuilder, MapSqlParameterSource params) {
        int offset = Math.abs(productFilter.getPageable().getPageNumber() * productFilter.getPageable().getPageSize());
        params.addValue("pageSize", productFilter.getPageable().getPageSize());
        params.addValue("offset", offset);

        queryBuilder.append(" ORDER BY p.name ASC LIMIT :pageSize OFFSET :offset");
        return queryBuilder.toString();
    }
}
