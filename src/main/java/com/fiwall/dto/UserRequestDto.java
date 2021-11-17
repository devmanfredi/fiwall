package com.fiwall.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserRequestDto {

    @Size(min = 1, max = 120)
    @NotNull
    private String fullName;

    @Size(min = 8)
    @NotNull
    @Email
    private String email;

    @NotNull
    private String password;

    @NotNull
    private String document;
}
