package com.icritic.ecritic_products_service.dataprovider.database.repository.impl;

import com.icritic.ecritic_products_service.dataprovider.database.entity.AttributeOptionEntity;
import com.icritic.ecritic_products_service.dataprovider.database.repository.AttributeOptionEntityCustomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class AttributeOptionEntityCustomRepositoryImpl implements AttributeOptionEntityCustomRepository {

    private final NamedParameterJdbcTemplate jdbcTemplate;

    private static final String INSERT = "INSERT INTO attribute_options (attribute, value) VALUES (CAST(:attribute AS attributes), :value) RETURNING id, attribute, value";

    @Override
    public AttributeOptionEntity saveEntity(AttributeOptionEntity attributeOptionEntity) {
        MapSqlParameterSource params = new MapSqlParameterSource();

        params.addValue("attribute", attributeOptionEntity.getAttribute());
        params.addValue("value", attributeOptionEntity.getValue());

        return jdbcTemplate.queryForObject(INSERT, params, new BeanPropertyRowMapper<>(AttributeOptionEntity.class));
    }
}
