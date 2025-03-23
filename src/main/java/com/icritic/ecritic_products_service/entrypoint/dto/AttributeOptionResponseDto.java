package com.icritic.ecritic_products_service.entrypoint.dto;

import com.icritic.ecritic_products_service.core.model.enums.Attribute;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class AttributeOptionResponseDto {

    private Long id;
    private String attribute;
    private String value;
}
