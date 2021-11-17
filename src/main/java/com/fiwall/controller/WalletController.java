package com.fiwall.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fiwall.config.rabbitmq.producer.RabbitMQProducerConfig;
import com.fiwall.dto.*;
import com.fiwall.model.Timeline;
import com.fiwall.model.Wallet;
import com.fiwall.service.AccountService;
import com.fiwall.service.TimelineService;
import com.fiwall.service.UserService;
import com.fiwall.service.WalletService;
import lombok.AllArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@AllArgsConstructor
@RestController
@RequestMapping("/wallet")
public class WalletController {

    public static final String TRANSFER_TRANSACTION = "Transfer";
    public static final String WITHDRAW_TRANSACTION = "Withdraw";
    public static final String DEPOSIT_TRANSACTION = "Deposit";
    public static final String PAYMENT_TRANSACTION = "Payment";
    public static final String SEM_SALDO_NA_CARTEIRA = "Sem Saldo na carteira";

    private final UserService userService;
    private final WalletService walletService;
    private final AccountService accountService;
    private final TimelineService timelineService;
    private final RabbitTemplate rabbitTemplate;

    @PostMapping(value = "/user/{userId}", produces = MediaType.APPLICATION_JSON_VALUE)
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
    public TransferResponseDto transfer(@RequestBody TransferRequestDto transferRequestDto) throws JsonProcessingException {
        var walletUserSender = walletService.getWallet(transferRequestDto.getSenderId());
        var walletUserReceiver = walletService.getWallet(transferRequestDto.getReceiverId());

        if (walletUserSender.getUser().getId().equals(walletUserReceiver.getUser().getId())) {
            walletUserReceiver.setBalance(transferRequestDto.getValue());
        } else if (walletUserSender.getBalance().compareTo(transferRequestDto.getValue()) > 0) {
            walletUserSender.setBalance(getSubtract(transferRequestDto, walletUserSender));
            walletUserReceiver.setBalance(walletUserReceiver.getBalance().add(transferRequestDto.getValue()));
        } else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, SEM_SALDO_NA_CARTEIRA);
        }


        walletService.updateBalance(walletUserReceiver);
        walletService.updateBalance(walletUserSender);

        var timeline = getTimeline(TRANSFER_TRANSACTION, walletUserSender.getBalance().toString(), walletUserSender, transferRequestDto.getValue().toString());
        timelineService.save(timeline);

        var email = EmailDto.builder()
                .ownerRef("Heuler")
                .emailFrom("felipemanfredigalaxy@gmail.com")
                .emailTo("manfredidev@gmail.com")
                .subject("Test Mensageria")
                .text("Hello RabbitMq")
                .build();
        rabbitTemplate.convertAndSend(RabbitMQProducerConfig.EXCHANGE_NAME, "", email);

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

        var timeline = getTimeline(WITHDRAW_TRANSACTION, wallet.getBalance().toString(), wallet, value.toString());
        timelineService.save(timeline);

        return getReceipt(value, wallet);

    }

    @PostMapping(value = "/deposit/{userId}/{value}")
    @ResponseStatus(code = HttpStatus.OK)
    public Map<String, Object> deposit(@PathVariable Long userId, @PathVariable BigDecimal value) {
        var wallet = walletService.getWallet(userId);

        wallet.setBalance(wallet.getBalance().add(value));

        walletService.updateBalance(wallet);

        var timeline = getTimeline(DEPOSIT_TRANSACTION, wallet.getBalance().toString(), wallet, value.toString());
        timelineService.save(timeline);

        return getReceipt(value, wallet);

    }

    @PostMapping(value = "/payment")
    @ResponseStatus(code = HttpStatus.OK)
    public Map<String, Object> payment(@RequestBody PaymentDto paymentDto) {
        var wallet = walletService.getWallet(paymentDto.getUserId());

        if (wallet.getBalance().compareTo(paymentDto.getValue()) > 0) {
            wallet.setBalance(wallet.getBalance().subtract(paymentDto.getValue()));
        }

        walletService.updateBalance(wallet);

        var timeline = getTimeline(PAYMENT_TRANSACTION, wallet.getBalance().toString(), wallet, paymentDto.getValue().toString());
        timelineService.save(timeline);

        return getReceipt(paymentDto.getValue(), wallet);
    }

    @GetMapping(value = "/timeline")
    @ResponseStatus(code = HttpStatus.OK)
    public Page<TimelineResponseDTO> timeline(
            @RequestParam UUID walletId,
            @RequestParam(value = "page", defaultValue = "0") Integer page,
            @RequestParam(value = "size", defaultValue = "20") Integer size
    ) {
        var sort = Sort.by(Sort.Direction.DESC, "dateTransaction");
        return walletService.getTimeline(walletId, page, size, sort).map(i -> {
            var timelineDto = new TimelineResponseDTO();
            BeanUtils.copyProperties(i, timelineDto);
            return timelineDto;
        });
    }

    private Map<String, Object> getReceipt(BigDecimal value, Wallet wallet) {
        Map<String, Object> receipt = new HashMap<>();
        receipt.put("Transaction realized on : ", LocalDateTime.now());
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

    private Timeline getTimeline(String action, String accountBalance, Wallet wallet, String valueTransaction) {
        return Timeline.builder()
                .action(action)
                .dateTransaction(LocalDateTime.now())
                .accountBalance(accountBalance)
                .walletId(wallet.getId())
                .valueTransaction(valueTransaction)
                .build();
    }
}
