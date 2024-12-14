package com.icritic.ecritic_products_service.entrypoint.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
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
public class ProductRequestDto {

    @NotNull(message = "BrandId is required")
    private Long brandId;

    @NotNull(message = "CategoryId is required")
    private Long categoryId;

    @NotBlank(message = "Name is required")
    @Length(min = 1, max = 200, message = "Name must be between 1 and 200 characters long")
    private String name;

    @NotBlank(message = "Description is required")
    @Length(min = 1, max = 2000, message = "Description must be between 1 and 2000 characters long")
    private String description;
}
