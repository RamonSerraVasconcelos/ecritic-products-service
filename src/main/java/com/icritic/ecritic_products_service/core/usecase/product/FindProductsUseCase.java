package com.icritic.ecritic_products_service.core.usecase.product;

import com.icritic.ecritic_products_service.core.model.Product;
import com.icritic.ecritic_products_service.core.model.ProductFilter;
import com.icritic.ecritic_products_service.core.usecase.product.boundary.CacheProductsBoundary;
import com.icritic.ecritic_products_service.core.usecase.product.boundary.FindCachedProductsBoundary;
import com.icritic.ecritic_products_service.core.usecase.product.boundary.FindProductsByParamsBoundary;
import com.icritic.ecritic_products_service.exception.DefaultException;
import com.icritic.ecritic_products_service.exception.InternalErrorException;
import com.icritic.ecritic_products_service.exception.handler.ErrorResponseCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class FindProductsUseCase {

    private final FindCachedProductsBoundary findCachedProductsBoundary;

    private final FindProductsByParamsBoundary findProductsByParamsBoundary;

    private final CountProductsUseCase countProductsUseCase;

    private final CacheProductsBoundary cacheProductsBoundary;

    private boolean shouldCacheProducts;

    public Page<Product> execute(ProductFilter productFilter) {
        log.info("Finding all products with parameters: [{}]", productFilter);

        try {
            List<Product> products = getProducts(productFilter);

            long count = countProductsUseCase.execute(productFilter);

            Page<Product> pageProducts = new PageImpl<>(products, productFilter.getPageable(), count);

            if (productFilter.isCacheable() && shouldCacheProducts) {
                cacheProductsBoundary.execute(pageProducts);
            }

            return pageProducts;
        } catch (DefaultException ex) {
            log.error("Error finding products. Exception: [{}]", ex.getErrorResponse());
            throw ex;
        } catch (Exception ex) {
            log.error("Error finding products", ex);
            throw new InternalErrorException(ErrorResponseCode.ECRITICPROD_09);
        }
    }

    private List<Product> getProducts(ProductFilter productFilter) {
        List<Product> products = new ArrayList<>();

        if (productFilter.isCacheable()) {
            products = findCachedProductsBoundary.execute(productFilter.getPageable());
            shouldCacheProducts = false;
        }

        if (products.isEmpty()) {
            products = findProductsByParamsBoundary.execute(productFilter);
            shouldCacheProducts = true;
        }

        return products;
    }
}
