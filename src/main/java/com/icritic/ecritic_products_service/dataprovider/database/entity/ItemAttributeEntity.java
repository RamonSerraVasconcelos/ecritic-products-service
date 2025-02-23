package com.icritic.ecritic_products_service.dataprovider.database.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.OffsetDateTime;

@Entity
@Table(name = "product_item_attributes")
@IdClass(ItemAttributeEntityId.class)
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ItemAttributeEntity {

    @Id
    @ManyToOne
    @JoinColumn(name = "item_id")
    private ItemEntity item;

    @Id
    @ManyToOne
    @JoinColumn(name = "attribute_option_id")
    private AttributeOptionEntity attributeOption;

    @CreationTimestamp
    private OffsetDateTime createdAt;

    @UpdateTimestamp
    private OffsetDateTime updatedAt;
}
