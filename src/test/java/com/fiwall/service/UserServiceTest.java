package com.fiwall.service;

import com.fiwall.builder.user.UserRequestBuilder;
import com.fiwall.dto.UserRequestDto;
import com.fiwall.model.User;
import com.fiwall.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;


@SpringBootTest
@ActiveProfiles("test")
class UserServiceTest {

    @Autowired
    private UserService userService;

    @MockBean
    private UserRepository userRepository;

    @Test
    void givenUserRequestDto_whenSave_thenReturnUserSaved() {
        UserRequestDto userDto = UserRequestBuilder.usuarioAdmin().build();
        var user = new User();
        BeanUtils.copyProperties(userDto, user);
        user.setPassword(userDto.getPassword());
        //TODO: verify encoding password
        when(userRepository.save(user)).thenReturn(user);

        User result = userService.save(userDto);

        assertNotNull(result);
        assertEquals(result.getEmail(), user.getEmail());
    }
}
