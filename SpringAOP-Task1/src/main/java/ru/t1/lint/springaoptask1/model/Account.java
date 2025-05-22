package ru.t1.lint.springaoptask1.model;

import jakarta.persistence.*;
import lombok.*;

@Entity(name = "accounts")
@Table
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Account extends AbstractEntity {

    @Column(name = "account_type", nullable = false)
    @Enumerated(EnumType.STRING)
    private AccountType accountType;

    @Column(name = "balance", nullable = false)
    private Double balance;

    @OneToOne
    private Client client;
}
