package com.icritic.ecritic_products_service.entrypoint.controller;

import com.icritic.ecritic_products_service.core.model.Brand;
import com.icritic.ecritic_products_service.core.model.BrandFilter;
import com.icritic.ecritic_products_service.core.model.enums.Role;
import com.icritic.ecritic_products_service.core.usecase.brand.CreateBrandUseCase;
import com.icritic.ecritic_products_service.core.usecase.brand.FindBrandsUseCase;
import com.icritic.ecritic_products_service.core.usecase.brand.UpdateBrandUseCase;
import com.icritic.ecritic_products_service.core.usecase.ValidateUserRoleUseCase;
import com.icritic.ecritic_products_service.entrypoint.dto.AuthorizationTokenData;
import com.icritic.ecritic_products_service.entrypoint.dto.BrandRequestDto;
import com.icritic.ecritic_products_service.entrypoint.dto.BrandResponseDto;
import com.icritic.ecritic_products_service.entrypoint.dto.Metadata;
import com.icritic.ecritic_products_service.entrypoint.dto.PageableResponse;
import com.icritic.ecritic_products_service.entrypoint.mapper.AuthorizationTokenDataMapper;
import com.icritic.ecritic_products_service.entrypoint.mapper.BrandDtoMapper;
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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.EnumSet;
import java.util.List;
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

    private final UpdateBrandUseCase updateBrandUseCase;

    private final FindBrandsUseCase findBrandsUseCase;

    @PostMapping
    public ResponseEntity<BrandResponseDto> createBrand(@RequestHeader("Authorization") String authorization,
                                                        @RequestBody BrandRequestDto brandRequestDto) {

        AuthorizationTokenData authorizationTokenData = authorizationTokenDataMapper.map(authorization);
        validateUserRoleUseCase.execute(EnumSet.of(Role.MODERATOR), authorizationTokenData.getUserRole());

        Set<ConstraintViolation<BrandRequestDto>> violations = validator.validate(brandRequestDto);
        if (!violations.isEmpty()) {
            throw new ResourceViolationException(violations);
        }

        Brand brand = createBrandUseCase.execute(brandDtoMapper.brandRequestDtoToBrand(brandRequestDto));

        return ResponseEntity.status(HttpStatus.CREATED).body(brandDtoMapper.brandToBrandResponseDto(brand));
    }

    @PutMapping("/{id}")
    public ResponseEntity<BrandResponseDto> updateBrand(@RequestHeader("Authorization") String authorization,
                                                        @PathVariable("id") Long id,
                                                        @RequestBody BrandRequestDto brandRequestDto) {

        AuthorizationTokenData authorizationTokenData = authorizationTokenDataMapper.map(authorization);
        validateUserRoleUseCase.execute(EnumSet.of(Role.MODERATOR), authorizationTokenData.getUserRole());

        Set<ConstraintViolation<BrandRequestDto>> violations = validator.validate(brandRequestDto);
        if (!violations.isEmpty()) {
            throw new ResourceViolationException(violations);
        }

        Brand brand = updateBrandUseCase.execute(id, brandDtoMapper.brandRequestDtoToBrand(brandRequestDto));

        return ResponseEntity.ok().body(brandDtoMapper.brandToBrandResponseDto(brand));
    }

    @GetMapping
    public ResponseEntity<PageableResponse<BrandResponseDto>> getBrands(Pageable pageable, @RequestParam(name = "name", required = false) String name) {
        BrandFilter brandFilter = BrandFilter
                .builder()
                .pageable(pageable)
                .name(name)
                .build();

        Page<Brand> brands = findBrandsUseCase.execute(brandFilter);

        List<BrandResponseDto> userResponseDtos = brands.getContent()
                .stream()
                .map(brandDtoMapper::brandToBrandResponseDto)
                .toList();

        PageableResponse<BrandResponseDto> pageableResponse = PageableResponse.<BrandResponseDto>builder()
                .data(userResponseDtos)
                .metadata(Metadata.builder()
                        .page(pageable.getPageNumber())
                        .nextPage(pageable.getPageNumber() + 1)
                        .size(pageable.getPageSize())
                        .total(brands.getTotalElements())
                        .build())
                .build();

        return ResponseEntity.status(HttpStatus.OK).body(pageableResponse);
    }
}
