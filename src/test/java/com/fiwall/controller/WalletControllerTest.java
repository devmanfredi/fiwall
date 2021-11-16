package com.fiwall.controller;

import com.fiwall.builder.account.AccountBuilder;
import com.fiwall.builder.user.UserBuilder;
import com.fiwall.builder.wallet.WalletBuilder;
import com.fiwall.dto.TransferRequestDto;
import com.fiwall.model.Account;
import com.fiwall.model.User;
import com.fiwall.model.Wallet;
import com.fiwall.repository.WalletRepository;
import com.fiwall.service.UserService;
import com.fiwall.service.WalletService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.UUID;

import static com.fiwall.util.TestUtil.convertObjectToJsonBytes;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@AutoConfigureMockMvc
@SpringBootTest
@ActiveProfiles("test")
class WalletControllerTest {

    public static final String PATH_WALLET = "/wallet";
    @Autowired
    private MockMvc mvc;

    @MockBean
    private UserService userService;

    @MockBean
    private WalletService walletService;

    @MockBean
    private WalletRepository walletRepository;

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
        wallet.setUser(user);
        wallet.setAccount(account);

        when(walletService.create(wallet)).thenReturn(wallet);
        when(userService.findUserById(user.getId())).thenReturn(user);


        String URI = "/wallet";

        ResultActions perform = mvc.perform(post(URI + "/" + user.getId()));
        perform.andExpect(status().is(201));

        perform.andExpect(jsonPath("$.user.id", is(10)));

    }

    @Test
    void givenUserId_whenWhenSearchWallet_shouldReturnWallet() throws Exception {
        wallet.setUser(user);
        wallet.setAccount(account);

        when(walletService.getWallet(user.getId())).thenReturn(wallet);

        ResultActions perform = mvc.perform(get(PATH_WALLET + "/" + user.getId()));
        perform.andExpect(status().is(201));

        perform.andExpect(jsonPath("$.balance", is(25000)));
        perform.andExpect(jsonPath("$.user.id", is(10)));


    }

    @Test
    void givenIdReceiver_whenFindWallet_shouldReturnWallet() throws Exception {
        //wallet sender
        wallet.setUser(user);
        wallet.setAccount(account);
        //wallet receiver
        var walletReceiver = new Wallet();

        var u1 = User.builder().email("u1@gmail.com").fullName("u1").id(1L).document("18846390032").build();
        var a1 = Account.builder().numberAccount(17894).agency(1).institution("BB Sa").id(UUID.randomUUID()).build();

        walletReceiver.setUser(u1);
        walletReceiver.setAccount(a1);
        walletReceiver.setBalance(BigDecimal.valueOf(13799));
        walletReceiver.setId(UUID.randomUUID());

        when(walletService.getWallet(user.getId())).thenReturn(wallet);
        when(walletService.getWallet(u1.getId())).thenReturn(walletReceiver);

        var transfReqDto = new TransferRequestDto();
        transfReqDto.setSenderId(wallet.getUser().getId());
        transfReqDto.setReceiverId(walletReceiver.getUser().getId());
        transfReqDto.setValue(BigDecimal.valueOf(10000));

        String URI = "/wallet/transfer";
        ResultActions perform = mvc.perform(post(URI)
                .contentType(MediaType.APPLICATION_JSON)
                .content(convertObjectToJsonBytes(transfReqDto)))
                .andExpect(status().isCreated());

        perform.andExpect(jsonPath("$.receiver", is(walletReceiver.getUser().getFullName())));
        perform.andExpect(jsonPath("$.value", is(10000)));

    }

    @Test
    void givenValue_whenHasBalance_shouldSubtractBalance() throws Exception {
        wallet.setUser(user);
        wallet.setAccount(account);

        when(walletService.getWallet(user.getId())).thenReturn(wallet);

        String URI = "/wallet/withdraw";
        ResultActions perform = mvc.perform(post(URI + "/" + user.getId() + "/" + 10000));
        perform.andExpect(status().isOk());

        perform.andExpect(jsonPath("$.Value", is(10000)));
    }

    @Test
    void givenValue_whenDeposit_shouldReturnDepositValue() throws Exception {
        wallet.setUser(user);
        wallet.setAccount(account);

        when(walletService.getWallet(user.getId())).thenReturn(wallet);

        String URI = "/wallet/deposit";
        ResultActions perform = mvc.perform(post(URI + "/" + user.getId() + "/" + 10000));
        perform.andExpect(status().isOk());

        perform.andExpect(jsonPath("$.Value", is(10000)));
    }
}
