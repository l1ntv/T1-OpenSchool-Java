package ru.t1.lint.springaoptask3.web.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import ru.t1.lint.springaoptask3.model.AccountType;

import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
public class AccountDTO {

    @NotNull(message = "Client ID cannot be null.")
    private UUID clientId;

    @NotNull(message = "Account type cannot be null.")
    private AccountType accountType;

    @NotNull(message = "Balance cannot be null.")
    @Min(value = 0, message = "Balance must be greater than or equal to 0.")
    private Double balance;
}
