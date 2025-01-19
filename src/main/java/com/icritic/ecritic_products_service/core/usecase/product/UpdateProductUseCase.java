package com.icritic.ecritic_products_service.core.usecase.product;

import com.icritic.ecritic_products_service.core.model.Product;
import com.icritic.ecritic_products_service.core.usecase.product.boundary.FindProductByIdBoundary;
import com.icritic.ecritic_products_service.core.usecase.product.boundary.InvalidateProductCacheBoundary;
import com.icritic.ecritic_products_service.core.usecase.product.boundary.InvalidateProductsCacheBoundary;
import com.icritic.ecritic_products_service.core.usecase.product.boundary.SaveProductBoundary;
import com.icritic.ecritic_products_service.exception.DefaultException;
import com.icritic.ecritic_products_service.exception.EntityNotFoundException;
import com.icritic.ecritic_products_service.exception.InternalErrorException;
import com.icritic.ecritic_products_service.exception.handler.ErrorResponseCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.time.OffsetDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class UpdateProductUseCase {

    private final FindProductByIdBoundary findProductByIdBoundary;

    private final SaveProductBoundary saveProductBoundary;

    private final InvalidateProductsCacheBoundary invalidateProductsCacheBoundary;

    private final InvalidateProductCacheBoundary invalidateProductCacheBoundary;

    public Product execute(Long id, Product product) {
        log.info("Updating product with id: [{}]", id);

        try {
            Optional<Product> optionalProduct = findProductByIdBoundary.execute(id);

            if (optionalProduct.isEmpty()) {
                throw new EntityNotFoundException(ErrorResponseCode.ECRITICPROD_15);
            }

            Product productToUpdate = optionalProduct.get();

            productToUpdate.setBrand(product.getBrand());
            productToUpdate.setCategory(product.getCategory());
            productToUpdate.setName(product.getName());
            productToUpdate.setDescription(product.getDescription());
            productToUpdate.setActive(product.isActive());
            productToUpdate.setUpdatedAt(OffsetDateTime.now());

            Product updatedProduct = saveProductBoundary.execute(productToUpdate);

            invalidateProductsCacheBoundary.execute();
            invalidateProductCacheBoundary.execute(productToUpdate.getId());

            return updatedProduct;
        } catch (DefaultException ex) {
            log.error("Error updating product. Exception: [{}]", ex.getErrorResponse());
            throw ex;
        } catch (Exception ex) {
            log.error("Error updating product", ex);
            throw new InternalErrorException(ErrorResponseCode.ECRITICPROD_09);
        }
    }
}
