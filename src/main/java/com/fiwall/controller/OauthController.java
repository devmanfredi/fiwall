package com.fiwall.controller;

import com.fiwall.dto.UserRequestDto;
import com.fiwall.dto.UserResponseDto;
import com.fiwall.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@AllArgsConstructor
@RestController
@RequestMapping
public class OauthController {

    private final UserService userService;

    @PostMapping(value = "/oauth/signup", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(code = HttpStatus.CREATED)
    public UserResponseDto save(@RequestBody UserRequestDto userRequestDto) {
        var user = userService.save(userRequestDto);
        var userResponseDto = new UserResponseDto();
        BeanUtils.copyProperties(user, userResponseDto);
        return userResponseDto;
    }
}
