package com.fiwall.repository;

import com.fiwall.model.Wallet;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface WalletRepository extends CrudRepository<Wallet, UUID> {


}
