package com.icritic.ecritic_products_service.entrypoint.controller;

import com.icritic.ecritic_products_service.core.model.Item;
import com.icritic.ecritic_products_service.core.model.ItemFilter;
import com.icritic.ecritic_products_service.core.model.enums.Role;
import com.icritic.ecritic_products_service.core.usecase.ValidateUserRoleUseCase;
import com.icritic.ecritic_products_service.core.usecase.item.CreateItemUseCase;
import com.icritic.ecritic_products_service.core.usecase.item.FindItemUseCase;
import com.icritic.ecritic_products_service.core.usecase.item.FindItemsUseCase;
import com.icritic.ecritic_products_service.core.usecase.item.UpdateItemUseCase;
import com.icritic.ecritic_products_service.entrypoint.dto.AuthorizationTokenData;
import com.icritic.ecritic_products_service.entrypoint.dto.ItemRequestDto;
import com.icritic.ecritic_products_service.entrypoint.dto.ItemResponseDto;
import com.icritic.ecritic_products_service.entrypoint.dto.Metadata;
import com.icritic.ecritic_products_service.entrypoint.dto.PageableResponse;
import com.icritic.ecritic_products_service.entrypoint.mapper.AuthorizationTokenDataMapper;
import com.icritic.ecritic_products_service.entrypoint.mapper.ItemDtoMapper;
import com.icritic.ecritic_products_service.exception.ResourceViolationException;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.EnumSet;
import java.util.List;
import java.util.Set;

@RestController
@RequiredArgsConstructor
public class ItemController {

    private final Validator validator;

    private final ValidateUserRoleUseCase validateUserRoleUseCase;

    private final AuthorizationTokenDataMapper authorizationTokenDataMapper;

    private final CreateItemUseCase createItemUseCase;

    private final FindItemUseCase findItemUseCase;

    private final FindItemsUseCase findItemsUseCase;

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

    @GetMapping("/{productId}/items")
    public ResponseEntity<PageableResponse<ItemResponseDto>> getItems(@RequestHeader("Authorization") String authorization,
                                                                      @PathVariable("productId") Long productId,
                                                                      @RequestParam(name = "active", required = false) Boolean active,
                                                                      @RequestParam(name = "name", required = false) String name,
                                                                      @RequestParam(name = "sku", required = false) String sku,
                                                                      @RequestParam(name = "priceMin", required = false) BigDecimal priceMin,
                                                                      @RequestParam(name = "priceMax", required = false) BigDecimal priceMax,
                                                                      Pageable pageable) {

        ItemFilter itemFilter = ItemFilter.builder()
                .productId(productId)
                .active(active)
                .name(name)
                .sku(sku)
                .priceMin(priceMin)
                .priceMax(priceMax)
                .pageable(pageable)
                .build();

        Page<Item> items = findItemsUseCase.execute(itemFilter);

        List<ItemResponseDto> itemResponseDtos = items.getContent().stream().map(itemDtoMapper::modelToResponse).toList();

        PageableResponse<ItemResponseDto> pageableResponse = PageableResponse.<ItemResponseDto>builder()
                .data(itemResponseDtos)
                .metadata(Metadata.builder()
                        .page(pageable.getPageNumber())
                        .nextPage(pageable.getPageNumber() + 1)
                        .size(pageable.getPageSize())
                        .total(items.getTotalElements())
                        .build())
                .build();

        return ResponseEntity.status(HttpStatus.OK).body(pageableResponse);
    }

    @PatchMapping("/{productId}/items/{id}")
    public ResponseEntity<ItemResponseDto> updateItem(@RequestHeader("Authorization") String authorization,
                                                      @PathVariable("productId") Long productId,
                                                      @RequestBody ItemRequestDto itemRequestDto) {
        AuthorizationTokenData authorizationTokenData = authorizationTokenDataMapper.map(authorization);
        validateUserRoleUseCase.execute(EnumSet.of(Role.MODERATOR), authorizationTokenData.getUserRole());

        Set<ConstraintViolation<ItemRequestDto>> violations = validator.validate(itemRequestDto);
        if (!violations.isEmpty()) {
            violations.forEach(violation -> {
                if (!violation.getPropertyPath().toString().equals("attributeOptions")) {
                    throw new ResourceViolationException(violation);
                }
            });
        }

        Item item = updateItemUseCase.execute(productId, itemRequestDto.getName(), itemRequestDto.getPrice(), itemRequestDto.getQuantity(), itemRequestDto.isActive());

        ItemResponseDto itemResponseDto = itemDtoMapper.modelToResponse(item);

        return ResponseEntity.status(HttpStatus.OK).body(itemResponseDto);
    }
}
