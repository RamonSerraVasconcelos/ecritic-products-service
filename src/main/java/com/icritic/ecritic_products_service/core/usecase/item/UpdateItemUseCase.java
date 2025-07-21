package com.icritic.ecritic_products_service.core.usecase.item;

import com.icritic.ecritic_products_service.core.model.Item;
import com.icritic.ecritic_products_service.core.usecase.item.boundary.FindItemByIdBoundary;
import com.icritic.ecritic_products_service.core.usecase.item.boundary.SaveItemBoundary;
import com.icritic.ecritic_products_service.exception.DefaultException;
import com.icritic.ecritic_products_service.exception.EntityNotFoundException;
import com.icritic.ecritic_products_service.exception.InternalErrorException;
import com.icritic.ecritic_products_service.exception.handler.ErrorResponseCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class UpdateItemUseCase {

    private final FindItemByIdBoundary findItemByIdBoundary;

    private final SaveItemBoundary saveItemBoundary;

    public Item execute(Long id, String name, BigDecimal price, long quantity, boolean active) {
        log.info("Updating product with id: [{}]", id);

        try {
            Optional<Item> optionalItem = findItemByIdBoundary.execute(id);

            if (optionalItem.isEmpty()) {
                throw new EntityNotFoundException(ErrorResponseCode.ECRITICPROD_19);
            }

            Item item = optionalItem.get();

            item.setName(name);
            item.setPrice(price);
            item.setQuantity(quantity);
            item.setActive(active);

            return saveItemBoundary.execute(item);
        } catch (DefaultException ex) {
            log.error("Error updating product. Exception: [{}]", ex.getErrorResponse());
            throw ex;
        } catch (Exception ex) {
            log.error("Error updating product", ex);
            throw new InternalErrorException(ErrorResponseCode.ECRITICPROD_09);
        }
    }
}
