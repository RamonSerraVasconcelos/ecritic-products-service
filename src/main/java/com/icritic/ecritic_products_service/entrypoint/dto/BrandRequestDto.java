package com.icritic.ecritic_products_service.entrypoint.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class BrandRequestDto {

    @NotBlank(message = "Name is required")
    @Length(min = 1, max = 200, message = "Name must be between 1 and 200 characters long")
    private String name;

    private String description;
}
