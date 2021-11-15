package com.fiwall.controller;

import com.fiwall.builder.user.AccountBuilder;
import com.fiwall.builder.user.UserBuilder;
import com.fiwall.builder.user.WalletBuilder;
import com.fiwall.dto.UserRequestDto;
import com.fiwall.model.Account;
import com.fiwall.model.User;
import com.fiwall.model.Wallet;
import com.fiwall.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

import static com.fiwall.util.TestUtil.convertObjectToJsonBytes;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@AutoConfigureMockMvc
@SpringBootTest
@ActiveProfiles("test")
class WalletControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private UserService userService;

    User user;
    Wallet wallet;
    Account account;


    @BeforeEach
    void setup() {
        user = UserBuilder.admin().build();
        wallet = WalletBuilder.wallet().build();
        account = AccountBuilder.account().build();
    }

    @Test
    @Transactional
    void givenUserRequestDto_whenCreateWallet_shouldReturnWallet() throws Exception {
        user.setId(UUID.randomUUID());
        var userRequestDto = new UserRequestDto();
        BeanUtils.copyProperties(user, userRequestDto);

        when(userService.findUserByDocument(userRequestDto.getDocument())).thenReturn(user);

        String URI = "/wallet";

        ResultActions perform = mvc.perform(post(URI)
                .contentType(MediaType.APPLICATION_JSON)
                .content(convertObjectToJsonBytes(userRequestDto)))
                .andExpect(status().isCreated());

        perform.andExpect(jsonPath("$.balance", is(0)));
        perform.andExpect(jsonPath("$.user.id", is(user.getId().toString())));


    }
}
