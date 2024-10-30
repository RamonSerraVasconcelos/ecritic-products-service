package com.icritic.ecritic_products_service.entrypoint.dto;

import com.icritic.ecritic_products_service.core.model.enums.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class AuthorizationTokenData {

    private UUID userId;
    private Role userRole;
}
