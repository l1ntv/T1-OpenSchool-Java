package ru.t1.lint.springaoptask2.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;
import ru.t1.lint.springaoptask2.model.AbstractEntity;

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
    private String lastName;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "patronymic")
    private String patronymic;
}
