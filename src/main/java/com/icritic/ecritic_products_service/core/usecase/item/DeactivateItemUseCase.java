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

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class DeactivateItemUseCase {

    private final FindItemByIdBoundary findItemByIdBoundary;

    private final SaveItemBoundary saveItemBoundary;

    public void execute(Long id) {
        log.info("Deactivating item with id: [{}]", id);

        try {
            Optional<Item> optionalItem = findItemByIdBoundary.execute(id);

            if (optionalItem.isEmpty()) {
                throw new EntityNotFoundException(ErrorResponseCode.ECRITICPROD_19);
            }

            Item item = optionalItem.get();

            item.setActive(false);

            saveItemBoundary.execute(item);
        } catch (DefaultException ex) {
            log.error("Error deactivating item. Exception: [{}]", ex.getErrorResponse());
            throw ex;
        } catch (Exception ex) {
            log.error("Error deactivating item", ex);
            throw new InternalErrorException(ErrorResponseCode.ECRITICPROD_09);
        }
    }
}
