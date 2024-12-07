package com.icritic.ecritic_products_service.core.usecase.category;

import com.icritic.ecritic_products_service.core.model.Category;
import com.icritic.ecritic_products_service.core.usecase.category.boundary.FindCategoryByIdBoundary;
import com.icritic.ecritic_products_service.core.usecase.category.boundary.InvalidateCategoriesCacheBoundary;
import com.icritic.ecritic_products_service.core.usecase.category.boundary.InvalidateCategoryCacheBoundary;
import com.icritic.ecritic_products_service.core.usecase.category.boundary.SaveCategoryBoundary;
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
public class UpdateCategoryUseCase {

    private final FindCategoryByIdBoundary findCategoryByIdBoundary;

    private final SaveCategoryBoundary saveCategoryBoundary;

    private final InvalidateCategoryCacheBoundary invalidateCategoryCacheBoundary;

    private final InvalidateCategoriesCacheBoundary invalidateCategoriesCacheBoundary;

    public Category execute(Long id, Category category) {
        log.info("Updating category with id: [{}]", id);

        try {
            Optional<Category> optionalCategory = findCategoryByIdBoundary.execute(id);

            if (optionalCategory.isEmpty()) {
                throw new EntityNotFoundException(ErrorResponseCode.ECRITICPROD_13);
            }

            Category categoryToUpdate = optionalCategory.get();
            categoryToUpdate.setName(category.getName());
            categoryToUpdate.setDescription(category.getDescription());

            Category savedCategory = saveCategoryBoundary.execute(categoryToUpdate);

            invalidateCategoryCacheBoundary.execute(id);
            invalidateCategoriesCacheBoundary.execute();

            return savedCategory;
        } catch (DefaultException ex) {
            log.error("Error updating category. Exception: [{}]", ex.getErrorResponse());
            throw ex;
        } catch (Exception ex) {
            log.error("Error updating category", ex);
            throw new InternalErrorException(ErrorResponseCode.ECRITICPROD_09);
        }
    }
}
