package com.fiwall.dto;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@NoArgsConstructor
@JsonPropertyOrder({"id", "action", "valueTransaction", "dateTransaction", "accountBalance", "walletId"})
public class TimelineResponseDTO {

    private Long id;

    private String action;

    private String accountBalance;

    private LocalDateTime dateTransaction;

    private String valueTransaction;

    private UUID walletId;
}
