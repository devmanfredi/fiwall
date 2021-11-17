package com.fiwall.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fiwall.builder.user.UserRequestBuilder;
import com.fiwall.dto.UserRequestDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.json.JacksonJsonParser;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.transaction.annotation.Transactional;

import static com.fiwall.util.TestUtil.convertObjectToJsonBytes;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@SpringBootTest
@ActiveProfiles("test")
class OauthControllerTest {

    @Autowired
    private MockMvc mvc;

    private final ObjectMapper objectMapper = new ObjectMapper();
    private final JacksonJsonParser parser = new JacksonJsonParser();

    @Test
    @Transactional
    void givenNewUser_whenRegister_shouldReturnUser() throws Exception {
        UserRequestDto user = UserRequestBuilder.usuarioAdmin().build();
        user.setEmail("abc@gmail.com");
        String URI = "/oauth/signup";
        ResultActions perform = mvc.perform(post(URI)
                .contentType(MediaType.APPLICATION_JSON)
                .content(convertObjectToJsonBytes(user)))
                .andExpect(status().is(201));
        perform.andExpect(jsonPath("$.email", is(user.getEmail())));
        perform.andExpect(jsonPath("$.password", is(user.getPassword())));
    }
}
