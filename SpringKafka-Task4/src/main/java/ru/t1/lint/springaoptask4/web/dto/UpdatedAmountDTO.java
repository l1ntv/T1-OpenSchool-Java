package ru.t1.lint.springaoptask4.web.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class UpdatedAmountDTO {

    @NotNull(message = "Amount cannot be null.")
    @Min(value = 0, message = "Amount must be greater than or equal to 0.")
    private Double amount;
}
