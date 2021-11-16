package com.fiwall.service;

import com.fiwall.dto.UserRequestDto;
import com.fiwall.model.Wallet;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Slf4j
@Service
public class DBService {

    @Autowired
    private UserService userService;

    @Autowired
    private WalletService walletService;

    @Autowired
    private TimelineService timelineService;

    @Autowired
    private AccountService accountService;


    public void instantiateDevDatabase() {
        log.info("Started database environment dev");

        var fullName = "Heuler Manfredi";
        var username = "hf@gmail.com";
        var password = "admin";

        UserRequestDto userDTO = UserRequestDto.builder().email(username).fullName(fullName).password(password).document("29989526079").build();
        var user = userService.save(userDTO);
        var account = accountService.createAccount();
        var wallet = Wallet.builder()
                .balance(BigDecimal.valueOf(50000))
                .account(account)
                .user(user).id(UUID.randomUUID())
                .build();

        walletService.create(wallet);

        List<UserRequestDto> users = mockUsers();
        for (UserRequestDto item : users) {
            var userTemp = userService.save(item);
            var accountTemp = accountService.createAccount();

            wallet.setAccount(accountTemp);
            wallet.setUser(userTemp);
            walletService.create(wallet);

        }


        log.info("=> Mock Finish");

    }

    public List<UserRequestDto> mockUsers() {
        List<UserRequestDto> users = new ArrayList<>();
        UserRequestDto userDTO1 = UserRequestDto.builder().email("dev1@gmail.com").fullName("Dev Dev 1").password("Dev1").document("07205048052").build();
        UserRequestDto userDTO2 = UserRequestDto.builder().email("dev2@gmail.com").fullName("Dev Dev 2").password("Dev2").document("49779575049").build();
        UserRequestDto userDTO3 = UserRequestDto.builder().email("dev3@gmail.com").fullName("Dev Dev 3").password("Dev3").document("18441781028").build();
        UserRequestDto userDTO4 = UserRequestDto.builder().email("dev4@gmail.com").fullName("Dev Dev 4").password("Dev4").document("67191033002").build();
        UserRequestDto userDTO5 = UserRequestDto.builder().email("dev5@gmail.com").fullName("Dev Dev 5").password("Dev5").document("67948466088").build();

        users.add(userDTO1);
        users.add(userDTO2);
        users.add(userDTO3);
        users.add(userDTO4);
        users.add(userDTO5);

        return users;
    }
}
