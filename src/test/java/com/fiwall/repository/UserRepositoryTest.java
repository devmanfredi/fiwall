package com.fiwall.repository;

import com.fiwall.builder.user.UserBuilder;
import com.fiwall.model.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;



@SpringBootTest
class UserRepositoryTest {

    @MockBean
    UserRepository userRepository;

    @DisplayName("Test User Repository => givenUser_whenSave_thenReturnEmail")
    @Test
    void givenUser_whenSave_thenReturnEmail() {
        User user = UserBuilder.admin().build();
        when(userRepository.save(user)).thenReturn(user);

        User result = userRepository.save(user);

        assertNotNull(result.getEmail());
        assertEquals(result.getEmail(), user.getEmail());

    }
}
