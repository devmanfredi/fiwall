package com.fiwall.service;

import com.fiwall.model.Wallet;
import com.fiwall.repository.WalletRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Service
@AllArgsConstructor
public class WalletService {

    private final WalletRepository walletRepository;

    @Transactional
    public Wallet create(Wallet wallet) {
        wallet.setBalance(BigDecimal.ZERO);
        return walletRepository.save(wallet);
    }

    public Wallet getWallet(Long id) {
        return walletRepository.findWalletByUserId(id);
    }
}
