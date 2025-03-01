package com.icritic.ecritic_products_service.core.usecase.item;

import com.icritic.ecritic_products_service.core.model.ItemAttribute;
import com.icritic.ecritic_products_service.core.usecase.item.boundary.FindItemAttributesBoundary;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class FindItemAttributesUseCase {

    private final FindItemAttributesBoundary findItemAttributesBoundary;

    public List<ItemAttribute> execute(Long itemId) {
        return findItemAttributesBoundary.execute(itemId);
    }
}
