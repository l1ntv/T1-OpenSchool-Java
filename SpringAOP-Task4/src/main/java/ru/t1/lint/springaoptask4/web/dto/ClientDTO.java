package ru.t1.lint.springaoptask4.web.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
public class ClientDTO {

    @NotNull(message = "Unique ID cannot be null.")
    private Long id;

    @NotNull(message = "Client ID cannot be null.")
    private UUID clientId;

    @Size(max = 255, message = "Max length of first name is 255.")
    private String firstName;

    @Size(max = 255, message = "Max length of last name is 255.")
    private String lastName;

    @Size(max = 255, message = "Max length of patronymic is 255.")
    private String patronymic;
}
