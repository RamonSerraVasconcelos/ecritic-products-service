package com.icritic.ecritic_products_service.core.usecase.item;

import com.icritic.ecritic_products_service.core.model.AttributeOption;
import com.icritic.ecritic_products_service.core.model.Item;
import com.icritic.ecritic_products_service.core.model.Product;
import com.icritic.ecritic_products_service.core.usecase.item.boundary.FindAttributeOptionByIdBoundary;
import com.icritic.ecritic_products_service.core.usecase.item.boundary.FindItemBySkuBoundary;
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

    public Item execute(Item item, List<Long> attributeOptionIds) {
        log.info("Creating item for product: [{}]", item.getProduct().getId());

        try {
            Product product = findProductByIdUseCase.execute(item.getProduct().getId());
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

            item.setActive(true);
            item.setCreatedAt(OffsetDateTime.now());
            item.setUpdatedAt(OffsetDateTime.now());

            Item createdItem = saveItemBoundary.execute(item);

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
