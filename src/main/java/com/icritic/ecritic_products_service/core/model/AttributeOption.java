package com.icritic.ecritic_products_service.core.model;

import com.icritic.ecritic_products_service.core.model.enums.Attribute;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import static java.util.Objects.nonNull;

@Builder
@Getter
@Setter
@AllArgsConstructor
public class AttributeOption {

    private Long id;
    private Attribute attribute;
    private String value;

    public AttributeOption(Attribute attribute, String value) {
        this.attribute = attribute;
        this.value = value.toUpperCase();
    }
}
