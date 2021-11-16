package com.fiwall.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity()
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Timeline {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    private String action;

    @NotNull
    private String accountBalance;

    @Email
    private LocalDateTime dateTransaction;

    @NotNull
    private String valueTransaction;

    @NotNull
    private UUID walletId;
}
