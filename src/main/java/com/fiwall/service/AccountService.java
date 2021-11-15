package com.fiwall.service;

import com.fiwall.model.Account;
import org.springframework.stereotype.Service;

import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

@Service
public class AccountService {

    public static final String PIC_PAY_PAGAMENTOS = "PicPay Pagamentos";

    public Account createAccount() {
        var account = new Account();
        account.setNumberAccount(generateNumber());
        account.setAgency(1);
        account.setInstitution(PIC_PAY_PAGAMENTOS);
        account.setId(UUID.randomUUID());
        return account;
    }

    private Integer generateNumber() {
        return ThreadLocalRandom.current().nextInt();
    }
}
