package com.fiwall.repository;

import com.fiwall.model.Timeline;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface TimelineRepository extends CrudRepository<Timeline, Long> {
    Page<Timeline> findAllByWalletId(UUID walletId, Pageable pageable);

}
