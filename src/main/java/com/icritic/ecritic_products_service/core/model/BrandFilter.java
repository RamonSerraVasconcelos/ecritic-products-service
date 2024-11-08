package com.icritic.ecritic_products_service.core.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.domain.Pageable;

import static java.util.Objects.isNull;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class BrandFilter {

    private String name;
    private Pageable pageable;

    public boolean isCacheable() {
        return isNull(name);
    }
}
