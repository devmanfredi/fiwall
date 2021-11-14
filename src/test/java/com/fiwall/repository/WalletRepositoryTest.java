package com.fiwall.repository;


import com.fiwall.builder.user.WalletBuilder;
import com.fiwall.model.Wallet;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

@SpringBootTest
@ActiveProfiles("test")
class WalletRepositoryTest {

    @MockBean
    WalletRepository walletRepository;

    @DisplayName("Test User Repository => givenUser_whenSave_thenReturnEmail")
    @Test
    void givenWallet_whenCreate_shouldReturnWallet() {
        Wallet wallet = WalletBuilder.wallet().build();
        when(walletRepository.save(wallet)).thenReturn(wallet);

        Wallet result = walletRepository.save(wallet);

        assertNotNull(result);
        assertEquals(result.getBalance(), wallet.getBalance());

    }
}
