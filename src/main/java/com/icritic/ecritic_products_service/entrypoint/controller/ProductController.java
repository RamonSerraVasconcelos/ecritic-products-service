package com.icritic.ecritic_products_service.entrypoint.controller;

import com.icritic.ecritic_products_service.core.model.Product;
import com.icritic.ecritic_products_service.core.model.ProductFilter;
import com.icritic.ecritic_products_service.core.model.enums.Role;
import com.icritic.ecritic_products_service.core.usecase.ValidateUserRoleUseCase;
import com.icritic.ecritic_products_service.core.usecase.product.CreateProductUseCase;
import com.icritic.ecritic_products_service.core.usecase.product.FindProductByIdUseCase;
import com.icritic.ecritic_products_service.core.usecase.product.FindProductsUseCase;
import com.icritic.ecritic_products_service.core.usecase.product.UpdateProductUseCase;
import com.icritic.ecritic_products_service.entrypoint.dto.AuthorizationTokenData;
import com.icritic.ecritic_products_service.entrypoint.dto.Metadata;
import com.icritic.ecritic_products_service.entrypoint.dto.PageableResponse;
import com.icritic.ecritic_products_service.entrypoint.dto.ProductRequestDto;
import com.icritic.ecritic_products_service.entrypoint.dto.ProductResponseDto;
import com.icritic.ecritic_products_service.entrypoint.mapper.AuthorizationTokenDataMapper;
import com.icritic.ecritic_products_service.entrypoint.mapper.ProductDtoMapper;
import com.icritic.ecritic_products_service.exception.ResourceViolationException;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.EnumSet;
import java.util.List;
import java.util.Set;

@RestController
@RequiredArgsConstructor
public class ProductController {

    private final Validator validator;

    private final ValidateUserRoleUseCase validateUserRoleUseCase;

    private final AuthorizationTokenDataMapper authorizationTokenDataMapper;

    private final ProductDtoMapper productDtoMapper;

    private final CreateProductUseCase createProductUseCase;

    private final UpdateProductUseCase updateProductUseCase;

    private final FindProductsUseCase findProductsUseCase;

    private final FindProductByIdUseCase findProductByIdUseCase;

    @PostMapping
    public ResponseEntity<ProductResponseDto> createProduct(@RequestHeader("Authorization") String authorization,
                                                            @RequestBody ProductRequestDto productRequestDto) {

        AuthorizationTokenData authorizationTokenData = authorizationTokenDataMapper.map(authorization);
        validateUserRoleUseCase.execute(EnumSet.of(Role.MODERATOR), authorizationTokenData.getUserRole());

        Set<ConstraintViolation<ProductRequestDto>> violations = validator.validate(productRequestDto);
        if (!violations.isEmpty()) {
            throw new ResourceViolationException(violations);
        }

        Product product = createProductUseCase.execute(productDtoMapper.productRequestDtoToProduct(productRequestDto));

        return ResponseEntity.status(HttpStatus.CREATED).body(productDtoMapper.productToProductResponseDto(product));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductResponseDto> updateProduct(@RequestHeader("Authorization") String authorization,
                                                            @PathVariable("id") Long id,
                                                            @RequestBody ProductRequestDto productRequestDto) {

        AuthorizationTokenData authorizationTokenData = authorizationTokenDataMapper.map(authorization);
        validateUserRoleUseCase.execute(EnumSet.of(Role.MODERATOR), authorizationTokenData.getUserRole());

        Set<ConstraintViolation<ProductRequestDto>> violations = validator.validate(productRequestDto);
        if (!violations.isEmpty()) {
            throw new ResourceViolationException(violations);
        }

        Product product = updateProductUseCase.execute(id, productDtoMapper.productRequestDtoToProduct(productRequestDto));

        return ResponseEntity.status(HttpStatus.OK).body(productDtoMapper.productToProductResponseDto(product));
    }

    @GetMapping
    public ResponseEntity<PageableResponse<ProductResponseDto>> getProducts(@RequestHeader("Authorization") String authorization,
                                                                            @RequestParam(name = "name", required = false) String name,
                                                                            @RequestParam(name = "brands", required = false) List<Long> brands,
                                                                            @RequestParam(name = "categories", required = false) List<Long> categories,
                                                                            @RequestParam(name = "active", required = false, defaultValue = "true") boolean active,
                                                                            Pageable pageable) {

        AuthorizationTokenData authorizationTokenData = authorizationTokenDataMapper.map(authorization);
        validateUserRoleUseCase.execute(EnumSet.of(Role.MODERATOR), authorizationTokenData.getUserRole());

        ProductFilter productFilter = ProductFilter.builder()
                .name(name)
                .brands(brands)
                .categories(categories)
                .active(active)
                .pageable(pageable)
                .build();

        Page<Product> products = findProductsUseCase.execute(productFilter);

        List<ProductResponseDto> productResponseDtos = products.getContent()
                .stream()
                .map(productDtoMapper::productToProductResponseDto)
                .toList();

        PageableResponse<ProductResponseDto> pageableResponse = PageableResponse.<ProductResponseDto>builder()
                .data(productResponseDtos)
                .metadata(Metadata.builder()
                        .page(pageable.getPageNumber())
                        .nextPage(pageable.getPageNumber() + 1)
                        .size(pageable.getPageSize())
                        .total(products.getTotalElements())
                        .build())
                .build();

        return ResponseEntity.status(HttpStatus.OK).body(pageableResponse);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductResponseDto> getProduct(@RequestHeader("Authorization") String authorization,
                                                         @PathVariable("id") Long id) {

        AuthorizationTokenData authorizationTokenData = authorizationTokenDataMapper.map(authorization);
        validateUserRoleUseCase.execute(EnumSet.of(Role.MODERATOR), authorizationTokenData.getUserRole());

        Product product = findProductByIdUseCase.execute(id);

        return ResponseEntity.status(HttpStatus.OK).body(productDtoMapper.productToProductResponseDto(product));
    }
}
