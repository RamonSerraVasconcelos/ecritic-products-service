package com.icritic.ecritic_products_service.core.model;

import com.icritic.ecritic_products_service.exception.BusinessViolationException;
import com.icritic.ecritic_products_service.exception.handler.ErrorResponseCode;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.List;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Item {

    private Long id;
    private Product product;
    private String name;
    private String sku;
    private BigDecimal price;
    private long quantity;
    private boolean active;
    private OffsetDateTime createdAt;
    private OffsetDateTime updatedAt;

    public void setSku(List<AttributeOption> attributeOptions) {
        this.sku = product.getId().toString();

        if (attributeOptions.isEmpty()) {
            throw new BusinessViolationException(ErrorResponseCode.ECRITICPROD_20);
        }

        attributeOptions.forEach(attributeOption -> {
            this.sku = this.sku.concat("-").concat(attributeOption.getId().toString());
        });
    }
}
