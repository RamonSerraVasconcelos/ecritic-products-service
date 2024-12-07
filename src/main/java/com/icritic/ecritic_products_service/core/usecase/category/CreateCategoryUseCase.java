package com.icritic.ecritic_products_service.core.usecase.category;

import com.icritic.ecritic_products_service.core.model.Category;
import com.icritic.ecritic_products_service.core.usecase.category.boundary.FindCategoryByNameBoundary;
import com.icritic.ecritic_products_service.core.usecase.category.boundary.InvalidateCategoriesCacheBoundary;
import com.icritic.ecritic_products_service.core.usecase.category.boundary.SaveCategoryBoundary;
import com.icritic.ecritic_products_service.exception.DefaultException;
import com.icritic.ecritic_products_service.exception.EntityConflictException;
import com.icritic.ecritic_products_service.exception.InternalErrorException;
import com.icritic.ecritic_products_service.exception.handler.ErrorResponseCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class CreateCategoryUseCase {

    private final FindCategoryByNameBoundary findCategoryByNameBoundary;

    private final SaveCategoryBoundary saveCategoryBoundary;

    private final InvalidateCategoriesCacheBoundary invalidateCategoriesCacheBoundary;

    public Category execute(Category category) {
        log.info("Creating category: [{}]", category.getName());

        try {
            Optional<Category> optionalCategory = findCategoryByNameBoundary.execute(category.getName());

            if (optionalCategory.isPresent()) {
                throw new EntityConflictException(ErrorResponseCode.ECRITICPROD_12);
            }

            Category cratedCategory = saveCategoryBoundary.execute(category);

            invalidateCategoriesCacheBoundary.execute();

            return cratedCategory;
        } catch (DefaultException ex) {
            log.error("Error creating category. Exception: [{}]", ex.getErrorResponse());
            throw ex;
        } catch (Exception ex) {
            log.error("Error creating category", ex);
            throw new InternalErrorException(ErrorResponseCode.ECRITICPROD_09);
        }
    }
}
