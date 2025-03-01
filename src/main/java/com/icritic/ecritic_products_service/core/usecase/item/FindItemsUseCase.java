package com.icritic.ecritic_products_service.core.usecase.item;

import com.icritic.ecritic_products_service.core.model.Item;
import com.icritic.ecritic_products_service.core.model.ItemFilter;
import com.icritic.ecritic_products_service.core.usecase.item.boundary.CountItemsByProductBoundary;
import com.icritic.ecritic_products_service.core.usecase.item.boundary.FindItemsByParamsBoundary;
import com.icritic.ecritic_products_service.exception.DefaultException;
import com.icritic.ecritic_products_service.exception.InternalErrorException;
import com.icritic.ecritic_products_service.exception.handler.ErrorResponseCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class FindItemsUseCase {

    private final FindItemsByParamsBoundary findItemsByParamsBoundary;

    private final CountItemsByProductBoundary countItemsByProductBoundary;

    public Page<Item> execute(ItemFilter itemFilter) {
        log.info("Finding items with parameters: [{}]", itemFilter.toString());

        try {
            List<Item> items = findItemsByParamsBoundary.execute(itemFilter);

            Long count = countItemsByProductBoundary.execute(itemFilter);

            Page<Item> pageItems = new PageImpl<>(items, itemFilter.getPageable(), count);

            return pageItems;
        } catch (DefaultException ex) {
            log.error("Error finding items. Exception: [{}]", ex.getErrorResponse());
            throw ex;
        } catch (Exception ex) {
            log.error("Error finding items", ex);
            throw new InternalErrorException(ErrorResponseCode.ECRITICPROD_09);
        }
    }
}
