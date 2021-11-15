package com.fiwall.builder.user;

import com.fiwall.model.Account;

import java.util.UUID;

public class AccountBuilder {
    public static final String PIC_PAY_PAGAMENTOS = "PicPay Pagamentos";
    private Account account;

    public static AccountBuilder account() {
        AccountBuilder builder = new AccountBuilder();
        builder.account = Account.builder()
                .id(UUID.randomUUID())
                .numberAccount(123456)
                .agency(1459)
                .institution(PIC_PAY_PAGAMENTOS)
                .build();
        return builder;
    }

    public Account build() {
        return account;
    }
}
