package com.icritic.ecritic_products_service.core.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.OffsetDateTime;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ItemAttribute {

    private Item item;
    private AttributeOption attributeOption;
    private OffsetDateTime createdAt;
    private OffsetDateTime updatedAt;
}
