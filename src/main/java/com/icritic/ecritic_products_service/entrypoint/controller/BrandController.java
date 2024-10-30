package com.icritic.ecritic_products_service.entrypoint.controller;

import com.icritic.ecritic_products_service.core.model.Brand;
import com.icritic.ecritic_products_service.core.model.enums.Role;
import com.icritic.ecritic_products_service.core.usecase.CreateBrandUseCase;
import com.icritic.ecritic_products_service.core.usecase.ValidateUserRoleUseCase;
import com.icritic.ecritic_products_service.entrypoint.dto.AuthorizationTokenData;
import com.icritic.ecritic_products_service.entrypoint.dto.BrandRequestDto;
import com.icritic.ecritic_products_service.entrypoint.dto.BrandResponseDto;
import com.icritic.ecritic_products_service.entrypoint.mapper.AuthorizationTokenDataMapper;
import com.icritic.ecritic_products_service.entrypoint.mapper.BrandDtoMapper;
import com.icritic.ecritic_products_service.exception.ResourceViolationException;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.EnumSet;
import java.util.Set;

@RestController
@RequestMapping("/brands")
@RequiredArgsConstructor
public class BrandController {

    private final Validator validator;

    private final ValidateUserRoleUseCase validateUserRoleUseCase;

    private final AuthorizationTokenDataMapper authorizationTokenDataMapper;

    private final BrandDtoMapper brandDtoMapper;

    private final CreateBrandUseCase createBrandUseCase;

    @PostMapping
    public ResponseEntity<BrandResponseDto> createBrand(@RequestHeader("Authorization") String authorization,
                                                        @RequestBody BrandRequestDto brandRequestDto) {

        Set<ConstraintViolation<BrandRequestDto>> violations = validator.validate(brandRequestDto);
        if (!violations.isEmpty()) {
            throw new ResourceViolationException(violations);
        }

        AuthorizationTokenData authorizationTokenData = authorizationTokenDataMapper.map(authorization);
        validateUserRoleUseCase.execute(EnumSet.of(Role.MODERATOR), authorizationTokenData.getUserRole());

        Brand brand = createBrandUseCase.execute(brandDtoMapper.brandRequestDtoToBrand(brandRequestDto));

        return ResponseEntity.status(HttpStatus.CREATED).body(brandDtoMapper.brandToBrandResponseDto(brand));
    }
}
