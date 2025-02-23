package com.icritic.ecritic_products_service.dataprovider.database.entity;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
public class ItemAttributeEntityId implements Serializable {

    private long item;
    private long attributeOption;
}
