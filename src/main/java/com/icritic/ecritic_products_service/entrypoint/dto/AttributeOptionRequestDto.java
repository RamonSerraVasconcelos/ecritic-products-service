package com.icritic.ecritic_products_service.entrypoint.dto;

import jakarta.validation.constraints.NotBlank;
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
public class AttributeOptionRequestDto {

    @NotBlank(message = "Attribute is required")
    private String attribute;

    @NotBlank(message = "Value is required")
    private String value;
}
