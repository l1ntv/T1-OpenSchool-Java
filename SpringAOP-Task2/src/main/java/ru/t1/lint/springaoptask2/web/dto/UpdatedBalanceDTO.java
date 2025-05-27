package ru.t1.lint.springaoptask2.web.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class UpdatedBalanceDTO {

    @NotNull(message = "Balance cannot be null.")
    @Min(value = 0, message = "Balance must be greater than or equal to 0.")
    private Double balance;
}
