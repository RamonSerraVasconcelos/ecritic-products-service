package com.icritic.ecritic_products_service.core.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.domain.Pageable;

import java.util.List;

import static java.util.Objects.isNull;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ProductFilter {

    private String name;
    private List<Long> brands;
    private List<Long> categories;
    private boolean active;
    private Pageable pageable;

    public boolean isCacheable() {
        return isNull(name) && isNull(brands) && isNull(categories) && active;
    }
}
