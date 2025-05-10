package com.icritic.ecritic_products_service.entrypoint.mapper;

import com.icritic.ecritic_products_service.core.model.Item;
import com.icritic.ecritic_products_service.entrypoint.dto.ItemRequestDto;
import com.icritic.ecritic_products_service.entrypoint.dto.ItemResponseDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ItemDtoMapper {

    Item requestToModel(ItemRequestDto itemRequestDto);

    ItemResponseDto modelToResponse(Item item);
}
