package com.fiwall.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TransferRequestDto {

    @NotNull
    private Long senderId;

    @NotNull
    private Long receiverId;

    @NotNull
    private BigDecimal value;
}
