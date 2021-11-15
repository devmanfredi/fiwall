package com.fiwall.service;

import com.fiwall.dto.UserRequestDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class DBService {

    @Autowired
    private UserService userService;


    public void instantiateDevDatabase() {
        log.info("Started database environment dev");

        var fullName = "Administrador";
        var username = "admin@admin.com";
        var password = "admin";

        UserRequestDto userDTO = UserRequestDto.builder().email(username).fullName(fullName).password(password).document("29989526079").build();
        userService.save(userDTO);
        List<UserRequestDto> users = mockUsers();
        for (UserRequestDto user : users) {
            userService.save(user);
        }

        log.info("=> Mock Finish");

    }

    public List<UserRequestDto> mockUsers() {
        List<UserRequestDto> users = new ArrayList<>();
        UserRequestDto userDTO1 = UserRequestDto.builder().email("dev1@gmail.com").fullName("Dev Dev 1").password("Dev1").document("07205048052").build();
        UserRequestDto userDTO2 = UserRequestDto.builder().email("dev2@gmail.com").fullName("Dev Dev 2").password("Dev2").document("49779575049").build();
        UserRequestDto userDTO3 = UserRequestDto.builder().email("dev3@gmail.com").fullName("Dev Dev 3").password("Dev3").document("18441781028").build();
        UserRequestDto userDTO4 = UserRequestDto.builder().email("dev4@gmail.com").fullName("Dev Dev 4").password("Dev4").document("67191033002").build();
        UserRequestDto userDTO5 = UserRequestDto.builder().email("dev5@gmail.com").fullName("Dev Dev 5").password("Dev5").document("67948466088").build();

        users.add(userDTO1);
        users.add(userDTO2);
        users.add(userDTO3);
        users.add(userDTO4);
        users.add(userDTO5);

        return users;
    }
}
