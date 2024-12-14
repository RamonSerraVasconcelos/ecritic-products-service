package com.icritic.ecritic_products_service.core.usecase.product;

import com.icritic.ecritic_products_service.core.model.Brand;
import com.icritic.ecritic_products_service.core.model.Category;
import com.icritic.ecritic_products_service.core.model.Product;
import com.icritic.ecritic_products_service.core.usecase.brand.FindBrandByIdUseCase;
import com.icritic.ecritic_products_service.core.usecase.category.FindCategoryByIdUseCase;
import com.icritic.ecritic_products_service.core.usecase.product.boundary.FindProductByNameBoundary;
import com.icritic.ecritic_products_service.core.usecase.product.boundary.InvalidateProductsCacheBoundary;
import com.icritic.ecritic_products_service.core.usecase.product.boundary.SaveProductBoundary;
import com.icritic.ecritic_products_service.exception.DefaultException;
import com.icritic.ecritic_products_service.exception.EntityConflictException;
import com.icritic.ecritic_products_service.exception.InternalErrorException;
import com.icritic.ecritic_products_service.exception.handler.ErrorResponseCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class CreateProductUseCase {

    private final FindProductByNameBoundary findProductByNameBoundary;

    private final FindBrandByIdUseCase findBrandByIdUseCase;

    private final FindCategoryByIdUseCase findCategoryByIdUseCase;

    private final SaveProductBoundary saveProductBoundary;

    private final InvalidateProductsCacheBoundary invalidateProductsCacheBoundary;

    public Product execute(Product product) {
        log.info("Creating product with name: [{}]", product.getName());

        try {
            Optional<Product> optionalProduct = findProductByNameBoundary.execute(product.getName());

            if (optionalProduct.isPresent()) {
                log.error("Product with name: [{}] already exists", product.getName());
                throw new EntityConflictException(ErrorResponseCode.ECRITICPROD_14);
            }

            Brand brand = findBrandByIdUseCase.execute(product.getBrand().getId());
            Category category = findCategoryByIdUseCase.execute(product.getCategory().getId());

            product.setBrand(brand);
            product.setCategory(category);
            product.setActive(true);
            product.setCreatedAt(OffsetDateTime.now());
            product.setUpdatedAt(OffsetDateTime.now());

            Product createdProduct = saveProductBoundary.execute(product);
            invalidateProductsCacheBoundary.execute();

            return createdProduct;
        } catch (DefaultException ex) {
            log.error("Error creating product. Exception: [{}]", ex.getErrorResponse());
            throw ex;
        } catch (Exception ex) {
            log.error("Error creating product", ex);
            throw new InternalErrorException(ErrorResponseCode.ECRITICPROD_09);
        }
    }
}
