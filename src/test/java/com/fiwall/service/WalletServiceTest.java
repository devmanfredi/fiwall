package com.fiwall.service;

import com.fiwall.builder.user.AccountBuilder;
import com.fiwall.builder.user.UserBuilder;
import com.fiwall.builder.user.WalletBuilder;
import com.fiwall.model.Account;
import com.fiwall.model.User;
import com.fiwall.model.Wallet;
import com.fiwall.repository.UserRepository;
import com.fiwall.repository.WalletRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

@SpringBootTest
@ActiveProfiles("test")
class WalletServiceTest {
    private static final int TRANSF_VALUE = 25000;

    @MockBean
    private UserRepository userRepository;

    @Autowired
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
    void givenWalletWithUserAndAccount_whenCreate_shouldReturnWallet() {

        user.setId(10L);
        wallet.setUser(user);
        wallet.setAccount(account);

        when(walletRepository.findWalletByUserId(user.getId())).thenReturn(wallet);

        Wallet result = walletService.getWallet(user.getId());

        assertNotNull(result);
        assertEquals(result.getUser().getId(), user.getId());

    }

    @Test
    void givenUser_whenFindWallet_shouldReturnWallet() {
        user = UserBuilder.admin().build();
        wallet = WalletBuilder.wallet().build();
        account = AccountBuilder.account().build();

        user.setId(10L);
        wallet.setUser(user);
        wallet.setAccount(account);

        when(walletRepository.save(wallet)).thenReturn(wallet);

        Wallet result = walletService.create(wallet);

        assertNotNull(result);
        assertEquals(result.getId(), wallet.getId());

    }

    /**
     * Transfer
     */
    @Test
    void givenIdReceiver_whenFindWallet_shouldReturnWallet() {
        when(userRepository.findById(user.getId())).thenReturn(Optional.of(user));

        wallet.setUser(user);
        wallet.setAccount(account);

        when(walletRepository.findWalletByUserId(wallet.getUser().getId())).thenReturn(wallet);

        Wallet result = walletService.getWallet(wallet.getUser().getId());

        assertNotNull(result);
        result.setBalance(BigDecimal.valueOf(TRANSF_VALUE));
        when(walletService.getWallet(wallet.getUser().getId())).thenReturn(result);
        Wallet resultWithTransfer = walletService.getWallet(wallet.getUser().getId());
        assertNotNull(resultWithTransfer);
        assertEquals(result.getBalance(), BigDecimal.valueOf(TRANSF_VALUE));
    }
}
