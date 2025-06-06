package ru.t1.lint.springaoptask4.web.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.UUID;

@Getter
@Setter
@Builder
public class TransactionAcceptInfoDTO {

    @NotNull(message = "Client ID cannot be null.")
    private UUID clientId;

    @NotNull(message = "Unique account ID cannot be null.")
    private Long accountId;

    @NotNull(message = "Unique transaction ID cannot be null.")
    private Long transactionId;

    @NotNull(message = "Transaction timestamp cannot be null.")
    private Date transactionDate;

    @NotNull(message = "Transaction amount cannot be null.")
    @Min(value = 0, message = "Transaction amount must be greater than or equal to 0.")
    private Double transactionAmount;

    @NotNull(message = "Account balance cannot be null.")
    @Min(value = 0, message = "Account balance must be greater than or equal to 0.")
    private Double accountBalance;
}
