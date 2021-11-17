package com.fiwall.builder.user;

import com.fiwall.model.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class UserBuilder {
    private BCryptPasswordEncoder bCrypt;
    private User user;

    public static UserBuilder admin() {
        UserBuilder builder = new UserBuilder();
        builder.bCrypt = new BCryptPasswordEncoder();
        builder.user = User.builder()
                .id(10L)
                .email("admin@admin.com")
                .fullName("Admin Admin")
                .document("02205652281")
                .password(builder.bCrypt.encode(("admin@2021")))
                .build();
        return builder;
    }

    public User build() {
        return user;
    }
}
