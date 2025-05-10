package com.icritic.ecritic_products_service.entrypoint.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.icritic.ecritic_products_service.core.model.Product;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ItemResponseDto {

    private Long id;
    private Long productId;
    private String name;
    private String sku;
    private BigDecimal price;
    private long quantity;
    private boolean active;
    private OffsetDateTime createdAt;
    private OffsetDateTime updatedAt;
}
