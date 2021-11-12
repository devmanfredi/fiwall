package com.fiwall.builder.user;

import com.fiwall.model.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.time.LocalDateTime;
import java.util.UUID;

public class UserBuilder {
    private BCryptPasswordEncoder bCrypt;
    private User user;

    public static UserBuilder admin() {
        UserBuilder builder = new UserBuilder();
        builder.bCrypt = new BCryptPasswordEncoder();
        builder.user = User.builder()
                .id(UUID.randomUUID())
                .email("admin@admin.com")
                .firstName("Admin")
                .lastName("Admin")
                .password(builder.bCrypt.encode(("admin")))
                .createdDate(LocalDateTime.now())
                .build();
        return builder;
    }

    public User build() {
        return user;
    }
}
