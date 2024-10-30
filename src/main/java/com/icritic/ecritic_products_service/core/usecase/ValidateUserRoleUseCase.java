package com.icritic.ecritic_products_service.core.usecase;

import com.icritic.ecritic_products_service.core.model.enums.Role;
import com.icritic.ecritic_products_service.exception.ForbiddenAccessException;
import com.icritic.ecritic_products_service.exception.handler.ErrorResponseCode;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.EnumSet;

@Service
@NoArgsConstructor
public class ValidateUserRoleUseCase {

    public void execute(EnumSet<Role> allowedRoles, Role currentRole) {
        if (currentRole == null) {
            throw new ForbiddenAccessException(ErrorResponseCode.ECRITICPROD_08);
        }

        if (currentRole == Role.ADMIN || allowedRoles.contains(currentRole)) {
            return;
        }

        throw new ForbiddenAccessException(ErrorResponseCode.ECRITICPROD_08);
    }
}
