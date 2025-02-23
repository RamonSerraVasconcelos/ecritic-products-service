package com.icritic.ecritic_products_service.core.model;

import com.icritic.ecritic_products_service.core.model.enums.Attribute;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AttributeOption {

    private Long id;
    private Attribute attribute;
    private String value;
}
