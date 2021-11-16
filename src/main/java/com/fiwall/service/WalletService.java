package com.fiwall.service;

import com.fiwall.model.Timeline;
import com.fiwall.model.Wallet;
import com.fiwall.repository.TimelineRepository;
import com.fiwall.repository.WalletRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.UUID;

@Service
@AllArgsConstructor
public class WalletService {

    private final WalletRepository walletRepository;
    private final TimelineRepository timelineRepository;

    @Transactional
    public Wallet create(Wallet wallet) {
        wallet.setBalance(BigDecimal.ZERO);
        return walletRepository.save(wallet);
    }

    public Wallet getWallet(Long id) {
        return walletRepository.findWalletByUserId(id);
    }

    public void updateBalance(Wallet wallet) {
        walletRepository.save(wallet);
    }

    public Page<Timeline> getTimeline(UUID id, Integer page, Integer size, Sort sort) {
        var pageRequest = PageRequest.of(page, size, sort);

        return timelineRepository.findAllByWalletId(id, pageRequest);
    }
}
