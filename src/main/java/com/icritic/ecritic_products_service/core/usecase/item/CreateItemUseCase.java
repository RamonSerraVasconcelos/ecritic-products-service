package com.icritic.ecritic_products_service.core.usecase.item;

import com.icritic.ecritic_products_service.core.model.AttributeOption;
import com.icritic.ecritic_products_service.core.model.Item;
import com.icritic.ecritic_products_service.core.model.ItemAttribute;
import com.icritic.ecritic_products_service.core.model.Product;
import com.icritic.ecritic_products_service.core.usecase.item.boundary.FindAttributeOptionByIdBoundary;
import com.icritic.ecritic_products_service.core.usecase.item.boundary.FindItemBySkuBoundary;
import com.icritic.ecritic_products_service.core.usecase.item.boundary.SaveItemAttributeBoundary;
import com.icritic.ecritic_products_service.core.usecase.item.boundary.SaveItemBoundary;
import com.icritic.ecritic_products_service.core.usecase.product.FindProductByIdUseCase;
import com.icritic.ecritic_products_service.exception.DefaultException;
import com.icritic.ecritic_products_service.exception.EntityConflictException;
import com.icritic.ecritic_products_service.exception.EntityNotFoundException;
import com.icritic.ecritic_products_service.exception.InternalErrorException;
import com.icritic.ecritic_products_service.exception.handler.ErrorResponseCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class CreateItemUseCase {

    private final FindProductByIdUseCase findProductByIdUseCase;

    private final FindAttributeOptionByIdBoundary findAttributeOptionByIdBoundary;

    private final FindItemBySkuBoundary findItemBySkuBoundary;

    private final SaveItemBoundary saveItemBoundary;

    private final SaveItemAttributeBoundary saveItemAttributeBoundary;

    public Item execute(Long productId, Item item, List<Long> attributeOptionIds) {
        log.info("Creating item for product: [{}]", productId);

        try {
            Product product = findProductByIdUseCase.execute(productId);
            item.setProduct(product);

            List<AttributeOption> attributeOptions = attributeOptionIds.stream()
                    .map(findAttributeOptionByIdBoundary::execute)
                    .map(attributeOption -> attributeOption.orElseThrow(() -> new EntityNotFoundException(ErrorResponseCode.ECRITICPROD_17)))
                    .toList();

            item.setSku(attributeOptions);

            Optional<Item> optionalItem = findItemBySkuBoundary.execute(item.getSku());

            if (optionalItem.isPresent()) {
                log.error("Item with sku: [{}] already exists", item.getSku());
                throw new EntityConflictException(ErrorResponseCode.ECRITICPROD_18);
            }

            OffsetDateTime dateNow = OffsetDateTime.now();

            item.setCreatedAt(dateNow);
            item.setUpdatedAt(dateNow);

            Item createdItem = saveItemBoundary.execute(item);

            attributeOptions.forEach(attributeOption -> {
                ItemAttribute itemAttribute = ItemAttribute.builder()
                        .item(createdItem)
                        .attributeOption(attributeOption)
                        .createdAt(dateNow)
                        .updatedAt(dateNow)
                        .build();
                saveItemAttributeBoundary.execute(itemAttribute);
            });

            return createdItem;
        } catch (DefaultException ex) {
            log.error("Error creating item. Exception: [{}]", ex.getErrorResponse());
            throw ex;
        } catch (Exception ex) {
            log.error("Error creating item", ex);
            throw new InternalErrorException(ErrorResponseCode.ECRITICPROD_09);
        }
    }
}
