package com.fiwall.controller;

import com.fiwall.dto.TransferRequestDto;
import com.fiwall.dto.TransferResponseDto;
import com.fiwall.model.Wallet;
import com.fiwall.service.AccountService;
import com.fiwall.service.UserService;
import com.fiwall.service.WalletService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@AllArgsConstructor
@RestController
@RequestMapping("/wallet")
public class WalletController {

    private final UserService userService;
    private final WalletService walletService;
    private final AccountService accountService;

    @PostMapping(value = "/{userId}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(code = HttpStatus.CREATED)
    public Wallet create(@PathVariable Long userId) {
        var wallet = new Wallet();
        var account = accountService.createAccount();
        var user = userService.findUserById(userId);

        wallet.setUser(user);
        wallet.setAccount(account);
        walletService.create(wallet);
        return wallet;
    }

    @GetMapping(value = "/{userId}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(code = HttpStatus.CREATED)
    public Wallet findWalletByUserId(@PathVariable Long userId) {
        return walletService.getWallet(userId);
    }

    @PostMapping(value = "/transfer", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(code = HttpStatus.CREATED)
    public TransferResponseDto transfer(@RequestBody TransferRequestDto transferRequestDto) {
        var walletUserSender = walletService.getWallet(transferRequestDto.getSenderId());
        var walletUserReceiver = walletService.getWallet(transferRequestDto.getReceiverId());

        if (walletUserReceiver.getBalance().compareTo(transferRequestDto.getValue()) > 0) {
            walletUserSender.setBalance(getSubtract(transferRequestDto, walletUserSender));
            walletUserReceiver.setBalance(walletUserReceiver.getBalance().add(transferRequestDto.getValue()));
        }

        walletService.updateBalance(walletUserReceiver);
        walletService.updateBalance(walletUserSender);

        return getTransferReceipt(transferRequestDto, walletUserSender, walletUserReceiver);
    }

    @PostMapping(value = "/withdraw/{userId}/{value}")
    @ResponseStatus(code = HttpStatus.OK)
    public Map<String, Object> withdraw(@PathVariable Long userId, @PathVariable BigDecimal value) {
        var wallet = walletService.getWallet(userId);
        if (wallet.getBalance().compareTo(BigDecimal.ZERO) > 0) {
            wallet.setBalance(wallet.getBalance().subtract(value));
        }

        walletService.updateBalance(wallet);

        return getReceipt(value, wallet);

    }

    @PostMapping(value = "/deposit/{userId}/{value}")
    @ResponseStatus(code = HttpStatus.OK)
    public Map<String, Object> deposit(@PathVariable Long userId, @PathVariable BigDecimal value) {
        var wallet = walletService.getWallet(userId);

        wallet.setBalance(wallet.getBalance().add(value));

        walletService.updateBalance(wallet);

        return getReceipt(value, wallet);

    }

    private Map<String, Object> getReceipt(BigDecimal value, Wallet wallet) {
        Map<String, Object> receipt = new HashMap<>();
        receipt.put("Operation realized on : ", LocalDateTime.now());
        receipt.put("Value", value);
        receipt.put("Account Balance : " + wallet.getAccount().getNumberAccount(), wallet.getBalance());
        return receipt;
    }

    private BigDecimal getSubtract(TransferRequestDto transferRequestDto, Wallet walletUserSender) {
        return walletUserSender.getBalance().subtract(transferRequestDto.getValue());
    }

    private TransferResponseDto getTransferReceipt(TransferRequestDto transferRequestDto, Wallet walletUserSender, Wallet walletUserReceiver) {
        return TransferResponseDto.builder()
                .sender(walletUserSender.getUser().getFullName())
                .receiver(walletUserReceiver.getUser().getFullName())
                .dateTransfer(LocalDateTime.now())
                .value(transferRequestDto.getValue()).build();
    }
}
