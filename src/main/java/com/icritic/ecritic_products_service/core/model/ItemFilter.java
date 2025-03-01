package com.icritic.ecritic_products_service.core.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ItemFilter {

    private Long productId;
    private String sku;
    private String name;
    private BigDecimal priceMin;
    private BigDecimal priceMax;
    private Boolean active;
    private Pageable pageable;
}
