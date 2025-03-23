package com.icritic.ecritic_products_service.entrypoint.controller;

import com.icritic.ecritic_products_service.core.model.AttributeOption;
import com.icritic.ecritic_products_service.core.model.enums.Attribute;
import com.icritic.ecritic_products_service.core.model.enums.Role;
import com.icritic.ecritic_products_service.core.usecase.ValidateUserRoleUseCase;
import com.icritic.ecritic_products_service.core.usecase.item.CreateAttributeOptionUseCase;
import com.icritic.ecritic_products_service.core.usecase.item.FindAttributeOptionsUseCase;
import com.icritic.ecritic_products_service.entrypoint.dto.AttributeOptionRequestDto;
import com.icritic.ecritic_products_service.entrypoint.dto.AttributeOptionResponseDto;
import com.icritic.ecritic_products_service.entrypoint.dto.AuthorizationTokenData;
import com.icritic.ecritic_products_service.entrypoint.dto.Metadata;
import com.icritic.ecritic_products_service.entrypoint.dto.PageableResponse;
import com.icritic.ecritic_products_service.entrypoint.mapper.AuthorizationTokenDataMapper;
import com.icritic.ecritic_products_service.exception.ResourceViolationException;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.EnumSet;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/attributes")
@RequiredArgsConstructor
public class AttributeController {

    private final Validator validator;

    private final ValidateUserRoleUseCase validateUserRoleUseCase;

    private final AuthorizationTokenDataMapper authorizationTokenDataMapper;

    private final CreateAttributeOptionUseCase createAttributeOptionUseCase;

    private final FindAttributeOptionsUseCase findAttributeOptionsUseCase;

    @PostMapping
    public ResponseEntity<AttributeOptionResponseDto> createAttributeOption(@RequestHeader("Authorization") String authorization,
                                                                            @RequestBody AttributeOptionRequestDto attributeOptionRequestDto) {
        AuthorizationTokenData authorizationTokenData = authorizationTokenDataMapper.map(authorization);
        validateUserRoleUseCase.execute(EnumSet.of(Role.MODERATOR), authorizationTokenData.getUserRole());

        Set<ConstraintViolation<AttributeOptionRequestDto>> violations = validator.validate(attributeOptionRequestDto);
        if (!violations.isEmpty()) {
            throw new ResourceViolationException(violations);
        }

        AttributeOption attributeOption = createAttributeOptionUseCase.execute(Attribute.parse(attributeOptionRequestDto.getAttribute()), attributeOptionRequestDto.getValue());

        AttributeOptionResponseDto attributeOptionResponseDto = AttributeOptionResponseDto.builder()
                .id(attributeOption.getId())
                .attribute(attributeOption.getAttribute().name())
                .value(attributeOption.getValue())
                .build();

        return ResponseEntity.status(HttpStatus.CREATED).body(attributeOptionResponseDto);
    }

    @GetMapping
    public ResponseEntity<PageableResponse<AttributeOptionResponseDto>> getAttributeOptions(@RequestHeader("Authorization") String authorization,
                                                                                            @RequestParam(value = "attribute") String attribute,
                                                                                            Pageable pageable) {

        Page<AttributeOption> attributeOptions = findAttributeOptionsUseCase.execute(pageable, Attribute.parse(attribute));

        List<AttributeOptionResponseDto> attributeOptionResponse = attributeOptions.getContent().stream()
                .map(attributeOption -> AttributeOptionResponseDto.builder()
                        .id(attributeOption.getId())
                        .attribute(attributeOption.getAttribute().name())
                        .value(attributeOption.getValue())
                        .build())
                .toList();

        PageableResponse<AttributeOptionResponseDto> pageableResponse = PageableResponse.<AttributeOptionResponseDto>builder()
                .data(attributeOptionResponse)
                .metadata(Metadata.builder()
                        .page(pageable.getPageNumber())
                        .nextPage(pageable.getPageNumber() + 1)
                        .size(pageable.getPageSize())
                        .total(attributeOptions.getTotalElements())
                        .build())
                .build();

        return ResponseEntity.status(HttpStatus.OK).body(pageableResponse);
    }
}
