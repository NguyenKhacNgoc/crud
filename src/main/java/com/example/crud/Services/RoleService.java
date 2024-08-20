package com.example.crud.services;

import java.util.Collections;

import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.resource.RolesResource;
import org.keycloak.admin.client.resource.UserResource;
import org.keycloak.representations.idm.RoleRepresentation;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class RoleService {
    Keycloak keycloak;
    @NonFinal
    @Value("${keycloak.realm}")
    String realm;

    public void assignRole(String userId, String roleName) {

        UserResource userResource = keycloak.realm(realm).users().get(userId);
        RolesResource rolesResource = keycloak.realm(realm).roles();
        RoleRepresentation representation = rolesResource.get(roleName).toRepresentation();
        userResource.roles().realmLevel().add(Collections.singletonList(representation));

    }

    public void deleteRoleFromUser(String userId, String roleName) {

        UserResource userResource = keycloak.realm(realm).users().get(userId);
        RolesResource rolesResource = keycloak.realm(realm).roles();
        RoleRepresentation representation = rolesResource.get(roleName).toRepresentation();
        userResource.roles().realmLevel().remove(Collections.singletonList(representation));
    }

}
