package com.fiwall.builder.user;

import com.fiwall.dto.UserRequestDto;

public class UserRequestBuilder {
    private UserRequestDto user;
    private UserRequestBuilder() {
    }

    public static UserRequestBuilder usuarioAdmin() {
        UserRequestBuilder builder = new UserRequestBuilder();
        builder.user = new UserRequestDto();
        builder.user.setFullName("Usuário Administrador");
        builder.user.setEmail("admin@admin.com");
        builder.user.setPassword("admin");
        builder.user.setDocument("02205652281");
        return builder;
    }

    public UserRequestDto build() {
        return user;
    }
}