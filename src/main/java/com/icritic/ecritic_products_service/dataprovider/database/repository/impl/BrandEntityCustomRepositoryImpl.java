package com.icritic.ecritic_products_service.dataprovider.database.repository.impl;

import com.icritic.ecritic_products_service.core.model.BrandFilter;
import com.icritic.ecritic_products_service.dataprovider.database.entity.BrandEntity;
import com.icritic.ecritic_products_service.dataprovider.database.repository.BrandEntityCustomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class BrandEntityCustomRepositoryImpl implements BrandEntityCustomRepository {

    private final NamedParameterJdbcTemplate jdbcTemplate;

    private static final String SELECT = "SELECT b.id, b.name, b.description FROM brands b";

    private static final String SELECT_COUNT = "SELECT COUNT(b.id) FROM brands b";

    @Override
    public List<BrandEntity> findBrandsByParams(BrandFilter brandFilter) {
        StringBuilder queryBuilder = new StringBuilder(SELECT);
        MapSqlParameterSource params = new MapSqlParameterSource();

        addConditions(brandFilter, queryBuilder, params);

        String query = getFinalQuery(brandFilter, queryBuilder, params);

        return jdbcTemplate.query(query, params, new BeanPropertyRowMapper<>(BrandEntity.class));
    }

    @Override
    public Long countBrandsByParams(BrandFilter brandFilter) {
        StringBuilder queryBuilder = new StringBuilder(SELECT_COUNT);
        MapSqlParameterSource params = new MapSqlParameterSource();

        addConditions(brandFilter, queryBuilder, params);

        return jdbcTemplate.queryForObject(queryBuilder.toString(), params, Long.class);
    }

    private void addConditions(BrandFilter brandFilter, StringBuilder queryBuilder, MapSqlParameterSource params) {
        if (StringUtils.hasText(brandFilter.getName())) {
            String name = brandFilter.getName();
            String trimmedName = name.substring(0, name.lastIndexOf(name.trim()) + name.trim().length()).replace(" ", " & ");

            params.addValue("name", trimmedName);

            queryBuilder.append(" WHERE to_tsvector(b.name) @@ to_tsquery(:name)");
        }
    }

    private String getFinalQuery(BrandFilter brandFilter, StringBuilder queryBuilder, MapSqlParameterSource params) {
        int offset = Math.abs(brandFilter.getPageable().getPageNumber() * brandFilter.getPageable().getPageSize());
        params.addValue("pageSize", brandFilter.getPageable().getPageSize());
        params.addValue("offset", offset);

        queryBuilder.append(" ORDER BY b.name ASC LIMIT :pageSize OFFSET :offset");
        return queryBuilder.toString();
    }
}
