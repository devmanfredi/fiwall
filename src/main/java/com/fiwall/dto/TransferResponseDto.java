package com.fiwall.dto;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonPropertyOrder({"sender", "receiver", "dateTransfer", "dateTransaction", "value"})
public class TransferResponseDto {

    private String sender;

    private String receiver;

    private LocalDateTime dateTransfer;

    private BigDecimal value;
}
