package com.fiwall.service;

import com.fiwall.model.Timeline;
import com.fiwall.model.Wallet;
import com.fiwall.repository.TimelineRepository;
import com.fiwall.repository.WalletRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.util.UUID;

@Service
public class WalletService {

    public static final String WALLET_NOT_FOUND = "Wallet Not Found";
    private final WalletRepository walletRepository;
    private final TimelineRepository timelineRepository;

    public WalletService(WalletRepository walletRepository, TimelineRepository timelineRepository) {
        this.walletRepository = walletRepository;
        this.timelineRepository = timelineRepository;
    }


    @Transactional
    public Wallet create(Wallet wallet) {
        wallet.setBalance(BigDecimal.ZERO);
        return walletRepository.save(wallet);
    }

    public Wallet getWallet(Long id) {
        return walletRepository.findWalletByUserId(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, WALLET_NOT_FOUND));
    }

    public void updateBalance(Wallet wallet) {
        walletRepository.save(wallet);
    }

    public Page<Timeline> getTimeline(UUID id, Integer page, Integer size, Sort sort) {
        var pageRequest = PageRequest.of(page, size, sort);

        return timelineRepository.findAllByWalletId(id, pageRequest);
    }

    public boolean isWalletExist(Long userId) {
        return walletRepository.findWalletByUserId(userId).isPresent();
    }
}
