package com.fiwall.service;

import com.fiwall.dto.UserRequestDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class DBService {

    @Autowired
    private UserService userService;

    public void instantiateDevDb() {
        log.info("Started database environment => Dev");

        var fullName = "Administrador";
        var username = "admin@admin.com";
        var password = "admin";

        UserRequestDto userDTO = UserRequestDto.builder().email(username).fullName(fullName).password(password).build();
        var user = userService.save(userDTO);
        log.info("User created: " + user);

        log.info("Finished database");

    }

    public void instantiateTestDb() {
        log.info("Started database environment => Test");

        var fullName = "Admin Test";
        var username = "admintest@admin.com";
        var password = "admintest";

        UserRequestDto userDTO = UserRequestDto.builder().email(username).fullName(fullName).password(password).build();
        var user = userService.save(userDTO);
        log.info("User created: " + user);

        log.info("Finished database");

    }
}
