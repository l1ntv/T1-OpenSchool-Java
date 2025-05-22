package ru.t1.lint.springaoptask1.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.UUID;

@Entity
@Table(name = "clients")
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Client extends AbstractEntity {

    @Column(nullable = false, unique = true)
    private UUID clientId;

    @Column(name = "last_name")
    @Size(max = 255, message = "Max length of last name is 255.")
    private String lastName;

    @Column(name = "first_name")
    @Size(max = 255, message = "Max length of first name is 255.")
    private String firstName;

    @Column(name = "patronymic")
    @Size(max = 255, message = "Max length of patronymic is 255.")
    private String patronymic;
}
