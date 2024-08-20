package com.example.crud.configuration;

import org.keycloak.OAuth2Constants;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.KeycloakBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class KeycloakConfig {
    @Value("${keycloak.auth-server-url}")
    private String serverUrl;
    @Value("${keycloak.admin.client-id}")
    private String adminClientId;
    @Value("${keycloak.admin.username}")
    private String username;
    @Value("${keycloak.admin.password}")
    private String password;

    @Bean
    public Keycloak getKeycloakInstance() {
        return KeycloakBuilder.builder()
                .serverUrl(serverUrl)
                .realm("master")
                .clientId(adminClientId)
                .username(username)
                .password(password)
                .grantType(OAuth2Constants.PASSWORD)
                .build();
    }

}
