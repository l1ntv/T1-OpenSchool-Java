package ru.t1.lint.springaoptask4.web.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import ru.t1.lint.springaoptask4.model.enums.AccountStatus;
import ru.t1.lint.springaoptask4.model.enums.AccountType;

import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
public class AccountDTO {

    @NotNull(message = "Unique ID cannot be null.")
    private Long id;

    @NotNull(message = "Client ID cannot be null.")
    private UUID clientId;

    @NotNull(message = "Account type cannot be null.")
    private AccountType accountType;

    @NotNull(message = "Account status cannot be null.")
    private AccountStatus accountStatus;

    @NotNull(message = "Frozen amount cannot be null.")
    @Min(value = 0, message = "Frozen amount must be greater than or equal to 0.")
    private Double frozenAmount;

    @NotNull(message = "Balance cannot be null.")
    @Min(value = 0, message = "Balance must be greater than or equal to 0.")
    private Double balance;
}
