package com.fiwall.repository;

import com.fiwall.model.Wallet;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WalletRepository extends CrudRepository<Wallet, Long> {

    Wallet findWalletByUserId(Long id);

}
