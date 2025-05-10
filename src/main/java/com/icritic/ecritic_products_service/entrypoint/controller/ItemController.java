package com.icritic.ecritic_products_service.entrypoint.controller;

import com.icritic.ecritic_products_service.core.model.Item;
import com.icritic.ecritic_products_service.core.model.enums.Role;
import com.icritic.ecritic_products_service.core.usecase.ValidateUserRoleUseCase;
import com.icritic.ecritic_products_service.core.usecase.item.CreateItemUseCase;
import com.icritic.ecritic_products_service.core.usecase.item.FindItemUseCase;
import com.icritic.ecritic_products_service.core.usecase.item.FindItemsUseCase;
import com.icritic.ecritic_products_service.core.usecase.item.UpdateItemUseCase;
import com.icritic.ecritic_products_service.entrypoint.dto.AuthorizationTokenData;
import com.icritic.ecritic_products_service.entrypoint.dto.ItemRequestDto;
import com.icritic.ecritic_products_service.entrypoint.dto.ItemResponseDto;
import com.icritic.ecritic_products_service.entrypoint.dto.PageableResponse;
import com.icritic.ecritic_products_service.entrypoint.mapper.AuthorizationTokenDataMapper;
import com.icritic.ecritic_products_service.entrypoint.mapper.ItemDtoMapper;
import com.icritic.ecritic_products_service.exception.ResourceViolationException;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import java.util.EnumSet;
import java.util.Set;

@RestController
@RequiredArgsConstructor
public class ItemController {

    private final Validator validator;

    private final ValidateUserRoleUseCase validateUserRoleUseCase;

    private final AuthorizationTokenDataMapper authorizationTokenDataMapper;

    private final CreateItemUseCase createItemUseCase;

    private final FindItemsUseCase findItemsUseCase;

    private final FindItemUseCase findItemUseCase;

    private final UpdateItemUseCase updateItemUseCase;

    private final ItemDtoMapper itemDtoMapper;

    @PostMapping("/{productId}/items")
    public ResponseEntity<ItemResponseDto> createItem(@RequestHeader("Authorization") String authorization,
                                                         @PathVariable("productId") Long productId,
                                                         @RequestBody ItemRequestDto itemRequestDto) {

        AuthorizationTokenData authorizationTokenData = authorizationTokenDataMapper.map(authorization);
        validateUserRoleUseCase.execute(EnumSet.of(Role.MODERATOR), authorizationTokenData.getUserRole());

        Set<ConstraintViolation<ItemRequestDto>> violations = validator.validate(itemRequestDto);
        if (!violations.isEmpty()) {
            throw new ResourceViolationException(violations);
        }

        Item item = createItemUseCase.execute(productId, itemDtoMapper.requestToModel(itemRequestDto), itemRequestDto.getAttributeOptions());

        ItemResponseDto itemResponseDto = itemDtoMapper.modelToResponse(item);

        return ResponseEntity.status(HttpStatus.CREATED).body(itemResponseDto);
    }
    @GetMapping("/{productId}/items/{id}")
    public ResponseEntity<ItemResponseDto> getItem(@RequestHeader("Authorization") String authorization,
                                                   @PathVariable("productId") Long productId,
                                                   @PathVariable("id") Long id) {

        Item item = findItemUseCase.execute(productId, id);

        ItemResponseDto itemResponseDto = itemDtoMapper.modelToResponse(item);

        return ResponseEntity.status(HttpStatus.OK).body(itemResponseDto);
    }
}
