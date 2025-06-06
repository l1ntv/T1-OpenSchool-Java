package ru.t1.lint.springaoptask4.model;

import jakarta.persistence.*;
import lombok.*;
import ru.t1.lint.springaoptask4.model.enums.TransactionStatus;

import java.util.Date;

@Entity(name = "transactions")
@Table
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Transaction extends AbstractEntity {

    @Column(name = "amount", nullable = false)
    private Double amount;

    @Column(name = "transaction_date", nullable = false)
    private Date transactionDate;

    @Column(name = "status", nullable = false)
    @Enumerated(EnumType.STRING)
    private TransactionStatus transactionStatus;

    @ManyToOne
    @JoinColumn(name = "client_id", referencedColumnName = "client_id")
    private Account account;
}
