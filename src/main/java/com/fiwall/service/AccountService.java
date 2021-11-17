package com.fiwall.service;

import com.fiwall.model.Account;
import com.fiwall.repository.AccountRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.concurrent.ThreadLocalRandom;

@Service
@AllArgsConstructor
public class AccountService {

    public static final String PIC_PAY_PAGAMENTOS = "PicPay Pagamentos";
    private final AccountRepository accountRepository;

    public Account createAccount() {
        var account = new Account();
        account.setNumberAccount(generateNumber());
        account.setAgency(1);
        account.setInstitution(PIC_PAY_PAGAMENTOS);
        accountRepository.save(account);
        return account;
    }

    private Integer generateNumber() {
        var numberAccount = ThreadLocalRandom.current().nextInt();
        if (numberAccount < 0) {
            numberAccount = numberAccount * -1;
        }
        return numberAccount;
    }
}
