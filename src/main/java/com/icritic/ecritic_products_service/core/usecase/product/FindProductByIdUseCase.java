package com.icritic.ecritic_products_service.core.usecase.product;

import com.icritic.ecritic_products_service.core.model.Product;
import com.icritic.ecritic_products_service.core.usecase.product.boundary.CacheProductBoundary;
import com.icritic.ecritic_products_service.core.usecase.product.boundary.FindCachedProductBoundary;
import com.icritic.ecritic_products_service.core.usecase.product.boundary.FindProductByIdBoundary;
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
public class FindProductByIdUseCase {

    private final FindCachedProductBoundary findCachedProductBoundary;

    private final FindProductByIdBoundary findProductByIdBoundary;

    private final CacheProductBoundary cacheProductBoundary;

    public Product execute(Long id) {
        log.info("Finding product by id: [{}]", id);

        try {
            Optional<Product> cachedProduct = findCachedProductBoundary.execute(id);

            if (cachedProduct.isPresent()) {
                log.info("Returning product from cache");
                return cachedProduct.get();
            }

            Optional<Product> product = findProductByIdBoundary.execute(id);

            if (product.isEmpty()) {
                throw new EntityNotFoundException(ErrorResponseCode.ECRITICPROD_15);
            }

            log.info("Product not found on cache, returning from database");
            cacheProductBoundary.execute(product.get());

            return product.get();
        } catch (DefaultException ex) {
            log.error("Error finding product by id. Exception: [{}]", ex.getErrorResponse());
            throw ex;
        } catch (Exception ex) {
            log.error("Error finding product by id", ex);
            throw new InternalErrorException(ErrorResponseCode.ECRITICPROD_09);
        }
    }
}
