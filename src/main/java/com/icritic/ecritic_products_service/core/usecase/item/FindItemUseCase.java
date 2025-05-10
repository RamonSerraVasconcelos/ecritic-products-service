package com.icritic.ecritic_products_service.core.usecase.item;

import com.icritic.ecritic_products_service.core.model.Item;
import com.icritic.ecritic_products_service.core.usecase.item.boundary.FindByProductAndItemBoundary;
import com.icritic.ecritic_products_service.exception.DefaultException;
import com.icritic.ecritic_products_service.exception.EntityNotFoundException;
import com.icritic.ecritic_products_service.exception.InternalErrorException;
import com.icritic.ecritic_products_service.exception.handler.ErrorResponseCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class FindItemUseCase {

    private final FindByProductAndItemBoundary findByProductAndItemBoundary;

    public Item execute(Long productId, Long itemId) {
        log.info("Finding item by product id: [{}] and item id: [{}]", productId, itemId);

        try {
            return findByProductAndItemBoundary.execute(productId, itemId)
                    .orElseThrow(() -> {
                        return new EntityNotFoundException(ErrorResponseCode.ECRITICPROD_19);
                    });
        } catch (DefaultException ex) {
            log.error("Error finding item. Exception: [{}]", ex.getErrorResponse());
            throw ex;
        } catch (Exception ex) {
            log.error("Error finding item", ex);
            throw new InternalErrorException(ErrorResponseCode.ECRITICPROD_09);
        }
    }
}
