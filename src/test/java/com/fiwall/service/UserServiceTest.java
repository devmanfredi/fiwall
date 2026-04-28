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
import org.springframework.web.server.ResponseStatusException;

import javax.persistence.EntityNotFoundException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


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

    @Test
    void givenDuplicateEmail_whenSave_shouldThrowBadRequest() {
        UserRequestDto userDto = UserRequestBuilder.usuarioAdmin().build();

        when(userRepository.findByEmail(userDto.getEmail())).thenReturn(Optional.of(new User()));

        assertThrows(ResponseStatusException.class, () -> userService.save(userDto));
    }

    @Test
    void givenDuplicateDocument_whenSave_shouldThrowBadRequest() {
        UserRequestDto userDto = UserRequestBuilder.usuarioAdmin().build();

        when(userRepository.findByEmail(userDto.getEmail())).thenReturn(Optional.empty());
        when(userRepository.findByDocument(userDto.getDocument())).thenReturn(Optional.of(new User()));

        assertThrows(ResponseStatusException.class, () -> userService.save(userDto));
    }

    @Test
    void givenInvalidUserId_whenFindUserById_shouldThrowEntityNotFound() {
        when(userRepository.findById(999L)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> userService.findUserById(999L));
    }
}
