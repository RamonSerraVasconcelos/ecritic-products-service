package com.icritic.ecritic_products_service.dataprovider.database.impl.item;

import com.icritic.ecritic_products_service.core.model.AttributeOption;
import com.icritic.ecritic_products_service.core.model.enums.Attribute;
import com.icritic.ecritic_products_service.core.usecase.item.boundary.FindAttributeOptionsBoundary;
import com.icritic.ecritic_products_service.dataprovider.database.entity.AttributeEntity;
import com.icritic.ecritic_products_service.dataprovider.database.entity.AttributeOptionEntity;
import com.icritic.ecritic_products_service.dataprovider.database.mapper.AttributeOptionEntityMapper;
import com.icritic.ecritic_products_service.dataprovider.database.repository.AttributeOptionEntityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class FindAttributeOptionsGateway implements FindAttributeOptionsBoundary {

    private final AttributeOptionEntityRepository attributeOptionRepository;

    private final AttributeOptionEntityMapper attributeOptionEntityMapper;

    @Override
    public Page<AttributeOption> execute(Pageable pageable, Attribute attribute) {
        Page<AttributeOptionEntity> attributeOptionEntities = attributeOptionRepository.findByAttribute(pageable, AttributeEntity.valueOf(attribute.name()));

        List<AttributeOption> attributeOptions = attributeOptionEntities.getContent().stream().map(attributeOptionEntityMapper::entityToModel).toList();

        return new PageImpl<>(attributeOptions, pageable, attributeOptionEntities.getTotalElements());
    }
}
