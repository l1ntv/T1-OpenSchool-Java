package ru.t1.lint.springaoptask4.model;

import jakarta.persistence.*;
import lombok.*;
import ru.t1.lint.springaoptask4.model.enums.AccountStatus;
import ru.t1.lint.springaoptask4.model.enums.AccountType;

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

    @Column(name = "account_status", nullable = false)
    @Enumerated(EnumType.STRING)
    private AccountStatus accountStatus;

    @Column(name = "frozen_amount", nullable = false)
    private Double frozenAmount;

    @Column(name = "balance", nullable = false)
    private Double balance;

    @ManyToOne
    @JoinColumn(name = "client_id", referencedColumnName = "clientId")
    private Client client;
}
