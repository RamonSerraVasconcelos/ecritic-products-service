package com.icritic.ecritic_products_service.core.usecase.category;

import com.icritic.ecritic_products_service.core.model.Category;
import com.icritic.ecritic_products_service.core.usecase.category.boundary.CacheCategoriesBoundary;
import com.icritic.ecritic_products_service.core.usecase.category.boundary.FindCachedCategoriesBoundary;
import com.icritic.ecritic_products_service.core.usecase.category.boundary.FindCategoriesBoundary;
import com.icritic.ecritic_products_service.exception.DefaultException;
import com.icritic.ecritic_products_service.exception.InternalErrorException;
import com.icritic.ecritic_products_service.exception.handler.ErrorResponseCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import static java.util.Objects.nonNull;

@Service
@RequiredArgsConstructor
@Slf4j
public class FindCategoriesUseCase {

    private final FindCachedCategoriesBoundary findCachedCategoriesBoundary;

    private final FindCategoriesBoundary findCategoriesBoundary;

    private final CacheCategoriesBoundary cacheCategoriesBoundary;

    public Page<Category> execute(Pageable pageable) {
        log.info("Finding all categories");

        try {
            Page<Category> categories = findCachedCategoriesBoundary.execute(pageable);

            if (nonNull(categories)) {
                log.info("Returning categories from cache");
                return categories;
            }

            log.info("Categories not found in cache, fetching from database");

            categories = findCategoriesBoundary.execute(pageable);

            cacheCategoriesBoundary.execute(categories);

            return categories;
        } catch (DefaultException ex) {
            log.error("Error finding categories. Exception: [{}]", ex.getErrorResponse());
            throw ex;
        } catch (Exception ex) {
            log.error("Error finding categories", ex);
            throw new InternalErrorException(ErrorResponseCode.ECRITICPROD_09);
        }
    }
}
