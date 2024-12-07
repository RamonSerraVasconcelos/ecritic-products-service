package com.icritic.ecritic_products_service.entrypoint.controller;

import com.icritic.ecritic_products_service.core.model.Category;
import com.icritic.ecritic_products_service.core.model.enums.Role;
import com.icritic.ecritic_products_service.core.usecase.ValidateUserRoleUseCase;
import com.icritic.ecritic_products_service.core.usecase.category.CreateCategoryUseCase;
import com.icritic.ecritic_products_service.core.usecase.category.FindCategoriesUseCase;
import com.icritic.ecritic_products_service.core.usecase.category.FindCategoryByIdUseCase;
import com.icritic.ecritic_products_service.core.usecase.category.UpdateCategoryUseCase;
import com.icritic.ecritic_products_service.entrypoint.dto.AuthorizationTokenData;
import com.icritic.ecritic_products_service.entrypoint.dto.CategoryRequestDto;
import com.icritic.ecritic_products_service.entrypoint.dto.CategoryResponseDto;
import com.icritic.ecritic_products_service.entrypoint.dto.Metadata;
import com.icritic.ecritic_products_service.entrypoint.dto.PageableResponse;
import com.icritic.ecritic_products_service.entrypoint.mapper.AuthorizationTokenDataMapper;
import com.icritic.ecritic_products_service.entrypoint.mapper.CategoryDtoMapper;
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
import org.springframework.web.bind.annotation.RestController;

import java.util.EnumSet;
import java.util.List;
import java.util.Set;

import static java.util.Objects.isNull;

@RestController
@RequestMapping("/categories")
@RequiredArgsConstructor
public class CategoryController {

    private final Validator validator;

    private final ValidateUserRoleUseCase validateUserRoleUseCase;

    private final AuthorizationTokenDataMapper authorizationTokenDataMapper;

    private final CategoryDtoMapper categoryDtoMapper;

    private final CreateCategoryUseCase createCategoryUseCase;

    private final UpdateCategoryUseCase updateCategoryUseCase;

    private final FindCategoriesUseCase findCategoriesUseCase;

    private final FindCategoryByIdUseCase findCategoryByIdUseCase;

    @PostMapping
    public ResponseEntity<CategoryResponseDto> createCategory(@RequestHeader("Authorization") String authorization,
                                                              @RequestBody CategoryRequestDto categoryResponseDto) {

        AuthorizationTokenData authorizationTokenData = authorizationTokenDataMapper.map(authorization);
        validateUserRoleUseCase.execute(EnumSet.of(Role.MODERATOR), authorizationTokenData.getUserRole());

        Set<ConstraintViolation<CategoryRequestDto>> violations = validator.validate(categoryResponseDto);
        if (!violations.isEmpty()) {
            throw new ResourceViolationException(violations);
        }

        Category category = createCategoryUseCase.execute(categoryDtoMapper.categoryRequestDtoToCategory(categoryResponseDto));

        return ResponseEntity.status(HttpStatus.CREATED).body(categoryDtoMapper.categoryToCategoryResponseDto(category));
    }

    @PutMapping("/{id}")
    public ResponseEntity<CategoryResponseDto> updateCategory(@RequestHeader("Authorization") String authorization,
                                                              @PathVariable("id") Long id,
                                                              @RequestBody CategoryRequestDto categoryRequestDto) {

        AuthorizationTokenData authorizationTokenData = authorizationTokenDataMapper.map(authorization);
        validateUserRoleUseCase.execute(EnumSet.of(Role.MODERATOR), authorizationTokenData.getUserRole());

        Set<ConstraintViolation<CategoryRequestDto>> violations = validator.validate(categoryRequestDto);
        if (!violations.isEmpty()) {
            throw new ResourceViolationException(violations);
        }

        Category category = updateCategoryUseCase.execute(id, categoryDtoMapper.categoryRequestDtoToCategory(categoryRequestDto));

        return ResponseEntity.status(HttpStatus.OK).body(categoryDtoMapper.categoryToCategoryResponseDto(category));
    }

    @GetMapping
    public ResponseEntity<PageableResponse<CategoryResponseDto>> getCategories(Pageable pageable) {
        Page<Category> categories = findCategoriesUseCase.execute(pageable);

        if (isNull(categories)) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }

        List<CategoryResponseDto> categoryResponseDtos = categories.getContent()
                .stream()
                .map(categoryDtoMapper::categoryToCategoryResponseDto)
                .toList();

        PageableResponse<CategoryResponseDto> pageableResponse = PageableResponse.<CategoryResponseDto>builder()
                .data(categoryResponseDtos)
                .metadata(Metadata.builder()
                        .page(pageable.getPageNumber())
                        .nextPage(pageable.getPageNumber() + 1)
                        .size(pageable.getPageSize())
                        .total(categories.getTotalElements())
                        .build())
                .build();

        return ResponseEntity.status(HttpStatus.OK).body(pageableResponse);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CategoryResponseDto> getCategory(@PathVariable("id") Long id) {
        Category category = findCategoryByIdUseCase.execute(id);

        return ResponseEntity.status(HttpStatus.OK).body(categoryDtoMapper.categoryToCategoryResponseDto(category));
    }
}
