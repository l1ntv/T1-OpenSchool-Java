package ru.t1.lint.springaoptask2.service;

import ru.t1.lint.springaoptask2.model.Transaction;

import java.util.List;
import java.util.UUID;

public interface TransactionService {

    List<Transaction> getAccountTransactions(UUID clientId);

    List<Transaction> getAllTransactions();

    Transaction createTransaction(Transaction transaction);

    void deleteTransaction(Long transactionId);

    Transaction updateAmount(Double amount, Long id);
}
