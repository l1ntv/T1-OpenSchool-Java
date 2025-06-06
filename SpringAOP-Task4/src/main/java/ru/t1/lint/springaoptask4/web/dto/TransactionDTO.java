package ru.t1.lint.springaoptask4.web.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import ru.t1.lint.springaoptask4.model.enums.TransactionStatus;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
public class TransactionDTO {

    @NotNull(message = "Unique ID cannot be null.")
    private Long id;

    @NotNull(message = "Client ID cannot be null.")
    private UUID clientId;

    @NotNull(message = "Amount cannot be null.")
    @Min(value = 0, message = "Amount must be greater than or equal to 0.")
    private Double amount;

    @NotNull(message = "Transaction status cannot be null.")
    private TransactionStatus transactionStatus;
}
