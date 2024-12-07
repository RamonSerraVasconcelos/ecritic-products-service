package com.icritic.ecritic_products_service.core.usecase.category;

import com.icritic.ecritic_products_service.core.model.Category;
import com.icritic.ecritic_products_service.core.usecase.category.boundary.CacheCategoryBoundary;
import com.icritic.ecritic_products_service.core.usecase.category.boundary.FindCachedCategoryBoundary;
import com.icritic.ecritic_products_service.core.usecase.category.boundary.FindCategoryByIdBoundary;
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
public class FindCategoryByIdUseCase {

    private final FindCachedCategoryBoundary findCachedCategoryBoundary;

    private final FindCategoryByIdBoundary findCategoryByIdBoundary;

    private final CacheCategoryBoundary cacheCategoryBoundary;

    public Category execute(Long id) {
        log.info("Finding category by id: [{}]", id);

        try {
            Optional<Category> cachedCategory = findCachedCategoryBoundary.execute(id);

            if (cachedCategory.isPresent()) {
                log.info("Returning category from cache");
                return cachedCategory.get();
            }

            Optional<Category> category = findCategoryByIdBoundary.execute(id);

            if (category.isEmpty()) {
                throw new EntityNotFoundException(ErrorResponseCode.ECRITICPROD_13);
            }

            log.info("Category not found on cache, returning from database");

            cacheCategoryBoundary.execute(category.get());

            return category.get();
        } catch (DefaultException ex) {
            log.error("Error finding category by id. Exception: [{}]", ex.getErrorResponse());
            throw ex;
        } catch (Exception ex) {
            log.error("Error finding category by id", ex);
            throw new InternalErrorException(ErrorResponseCode.ECRITICPROD_09);
        }
    }
}
