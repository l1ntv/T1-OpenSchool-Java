package ru.t1.lint.springaoptask2.web.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
public class TransactionDTO {

    @NotNull(message = "Client ID cannot be null.")
    private UUID clientId;

    @NotNull(message = "Amount cannot be null.")
    @Min(value = 0, message = "Amount must be greater than or equal to 0.")
    private Double amount;
}
