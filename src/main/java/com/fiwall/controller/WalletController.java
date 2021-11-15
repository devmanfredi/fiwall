package com.fiwall.controller;

import com.fiwall.dto.UserRequestDto;
import com.fiwall.model.Wallet;
import com.fiwall.service.AccountService;
import com.fiwall.service.UserService;
import com.fiwall.service.WalletService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@AllArgsConstructor
@RestController
@RequestMapping("/wallet")
public class WalletController {

    private final UserService userService;
    private final WalletService walletService;
    private final AccountService accountService;

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(code = HttpStatus.CREATED)
    public Wallet create(@RequestBody UserRequestDto userRequestDto) {
        var wallet = new Wallet();
        var account = accountService.createAccount();
        var user = userService.findUserByDocument(userRequestDto.getDocument());

        wallet.setUser(user);
        wallet.setAccount(account);
        walletService.create(wallet);
        return wallet;
    }
}
