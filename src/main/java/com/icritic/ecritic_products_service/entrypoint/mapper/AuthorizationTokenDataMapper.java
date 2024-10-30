package com.icritic.ecritic_products_service.entrypoint.mapper;

import com.icritic.ecritic_products_service.core.model.enums.Role;
import com.icritic.ecritic_products_service.entrypoint.dto.AuthorizationTokenData;
import com.icritic.ecritic_products_service.entrypoint.validation.TokenUtils;
import com.icritic.ecritic_products_service.exception.UnauthorizedAccessException;
import com.icritic.ecritic_products_service.exception.handler.ErrorResponseCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@Slf4j
public class AuthorizationTokenDataMapper {

    public AuthorizationTokenData map(String authorizationToken) {
        try {
            UUID userId = UUID.fromString(TokenUtils.getUserIdFromToken(authorizationToken));
            String userRole = TokenUtils.getUserRoleFromToken(authorizationToken);

            return AuthorizationTokenData.builder()
                    .userId(userId)
                    .userRole(Role.parseRole(userRole))
                    .build();
        } catch (Exception ex) {
            log.error("Error parsing access token", ex);
            throw new UnauthorizedAccessException(ErrorResponseCode.ECRITICPROD_03);
        }
    }
}
