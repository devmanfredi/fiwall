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
public class PaymentDto {

    @NotNull
    private Long userId;

    @NotNull
    private String barCode;

    @NotNull
    private String description;

    @NotNull
    private BigDecimal value;
}
