package ru.t1.lint.springaoptask2.model;

import jakarta.persistence.*;
import lombok.*;
import ru.t1.lint.springaoptask2.model.AbstractEntity;

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

    @ManyToOne
    @JoinColumn(name = "client_id", referencedColumnName = "client_id")
    private Account account;
}
