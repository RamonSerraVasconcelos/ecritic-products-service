package com.icritic.ecritic_products_service.entrypoint.controller;

import com.icritic.ecritic_products_service.core.model.Product;
import com.icritic.ecritic_products_service.core.model.enums.Role;
import com.icritic.ecritic_products_service.core.usecase.ValidateUserRoleUseCase;
import com.icritic.ecritic_products_service.core.usecase.product.CreateProductUseCase;
import com.icritic.ecritic_products_service.entrypoint.dto.AuthorizationTokenData;
import com.icritic.ecritic_products_service.entrypoint.dto.ProductRequestDto;
import com.icritic.ecritic_products_service.entrypoint.dto.ProductResponseDto;
import com.icritic.ecritic_products_service.entrypoint.mapper.AuthorizationTokenDataMapper;
import com.icritic.ecritic_products_service.entrypoint.mapper.ProductDtoMapper;
import com.icritic.ecritic_products_service.exception.ResourceViolationException;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import java.util.EnumSet;
import java.util.Set;

@RestController
@RequiredArgsConstructor
public class ProductController {

    private final Validator validator;

    private final ValidateUserRoleUseCase validateUserRoleUseCase;

    private final AuthorizationTokenDataMapper authorizationTokenDataMapper;

    private final ProductDtoMapper productDtoMapper;

    private final CreateProductUseCase createProductUseCase;

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

        return ResponseEntity.status(HttpStatus.OK).body(productDtoMapper.productToProductResponseDto(product));
    }
}
