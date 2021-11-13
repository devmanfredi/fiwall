package com.fiwall.controller;

import com.fiwall.dto.UserRequestDto;
import com.fiwall.dto.UserResponseDto;
import com.fiwall.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@AllArgsConstructor
@RestController
@RequestMapping(value = "/user")
public class UserController {

    private final UserService userService;

    @PostMapping
    @ResponseStatus(code = HttpStatus.CREATED)
    public UserResponseDto save(@RequestBody UserRequestDto userRequestDto) {
        var user = userService.save(userRequestDto);
        var userResponseDto = new UserResponseDto();
        BeanUtils.copyProperties(user, userResponseDto);
        return userResponseDto;
    }
}
