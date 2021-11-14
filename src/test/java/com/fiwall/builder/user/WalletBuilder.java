package com.fiwall.builder.user;

import com.fiwall.model.Wallet;

import java.math.BigDecimal;
import java.util.UUID;

public class WalletBuilder {
    private Wallet wallet;

    public static WalletBuilder wallet() {
        WalletBuilder builder = new WalletBuilder();
        builder.wallet = Wallet.builder()
                .id(UUID.randomUUID())
                .user(null)
                .account(null)
                .balance(BigDecimal.valueOf(25000))
                .build();
        return builder;
    }

    public Wallet build() {
        return wallet;
    }

}
