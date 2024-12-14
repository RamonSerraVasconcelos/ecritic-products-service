package com.icritic.ecritic_products_service.core.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.OffsetDateTime;
import java.util.List;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Category {

    private Long id;
    private String name;
    private String description;
    private Long parentId;
    private OffsetDateTime createdAt;
    private OffsetDateTime updatedAt;
}
