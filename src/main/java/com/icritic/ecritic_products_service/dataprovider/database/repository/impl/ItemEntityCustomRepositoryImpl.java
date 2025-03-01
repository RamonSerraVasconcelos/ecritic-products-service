package com.icritic.ecritic_products_service.dataprovider.database.repository.impl;

import com.icritic.ecritic_products_service.core.model.ItemFilter;
import com.icritic.ecritic_products_service.dataprovider.database.entity.ItemEntity;
import com.icritic.ecritic_products_service.dataprovider.database.repository.ItemEntityCustomRepository;
import io.netty.util.internal.StringUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import java.util.List;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

@Repository
@RequiredArgsConstructor
public class ItemEntityCustomRepositoryImpl implements ItemEntityCustomRepository {

    private final NamedParameterJdbcTemplate jdbcTemplate;

    private final static String SELECT = "SELECT i.id, i.product_id, i.name, i.sku, i.price, i.quantity, i.active FROM product_items i";

    private final static String SELECT_COUNT = "SELECT COUNT(i.id) FROM product_items i";

    @Override
    public List<ItemEntity> findItemsByParams(ItemFilter itemFilter) {
        StringBuilder queryBuilder = new StringBuilder(SELECT);
        MapSqlParameterSource params = new MapSqlParameterSource();

        addConditions(itemFilter, queryBuilder, params);

        String query = getFinalQuery(itemFilter, queryBuilder, params);

        return jdbcTemplate.query(query, params, new BeanPropertyRowMapper<>(ItemEntity.class));
    }

    @Override
    public Long countItemsByParams(ItemFilter itemFilter) {
        StringBuilder queryBuilder = new StringBuilder(SELECT_COUNT);
        MapSqlParameterSource params = new MapSqlParameterSource();

        addConditions(itemFilter, queryBuilder, params);

        return jdbcTemplate.queryForObject(queryBuilder.toString(), params, Long.class);
    }

    private void addConditions(ItemFilter itemFilter, StringBuilder queryBuilder, MapSqlParameterSource params) {
        queryBuilder.append(" WHERE i.active in (:active)");
        params.addValue("active", isNull(itemFilter.getActive()) ? List.of(true, false) : itemFilter.getActive());

        if (StringUtils.hasText(itemFilter.getName())) {
            queryBuilder.append(" AND to_tsvector(i.name) @@ to_tsquery(:name)");
            params.addValue("name", itemFilter.getName());
        }

        if (StringUtils.hasText(itemFilter.getSku())) {
            queryBuilder.append(" AND to_tsvector(i.sku) @@ to_tsquery(:sku)");
            params.addValue("sku", itemFilter.getSku());
        }

        if (itemFilter.getProductId() != null) {
            queryBuilder.append(" AND i.product_id = :productId");
            params.addValue("productId", itemFilter.getProductId());
        }

        if (nonNull(itemFilter.getPriceMin())) {
            queryBuilder.append(" AND i.price >= :priceMin");
            params.addValue("priceMin", itemFilter.getPriceMin());
        }

        if (nonNull(itemFilter.getPriceMax())) {
            queryBuilder.append(" AND i.price <= :priceMax");
            params.addValue("priceMax", itemFilter.getPriceMax());
        }
    }

    private String getFinalQuery(ItemFilter itemFilter, StringBuilder queryBuilder, MapSqlParameterSource params) {
        int offset = Math.abs(itemFilter.getPageable().getPageNumber() * itemFilter.getPageable().getPageSize());
        params.addValue("pageSize", itemFilter.getPageable().getPageSize());
        params.addValue("offset", offset);

        queryBuilder.append(" ORDER BY i.name ASC LIMIT :pageSize OFFSET :offset");
        return queryBuilder.toString();
    }
}
