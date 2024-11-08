package com.icritic.ecritic_products_service.config.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "application.properties.redis")
@Getter
@Setter
public class RedisCacheProperties {

    private int brandExpirationTime;
    private int brandsExpirationTime;
    private int brandsCountExpirationTime;
}
