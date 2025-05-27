package ru.t1.lint.springaoptask1.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.t1.lint.springaoptask1.aop.DataSourceErrorLoggable;
import ru.t1.lint.springaoptask1.model.Account;
import ru.t1.lint.springaoptask1.model.Transaction;
import ru.t1.lint.springaoptask1.model.exception.AccountNotFoundException;
import ru.t1.lint.springaoptask1.model.exception.TransactionNotFoundException;
import ru.t1.lint.springaoptask1.repository.AccountRepository;
import ru.t1.lint.springaoptask1.repository.TransactionRepository;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TransactionServiceImpl implements TransactionService {

    private final TransactionRepository transactionRepository;

    private final AccountRepository accountRepository;

    @Override
    @DataSourceErrorLoggable
    public List<Transaction> getAccountTransactions(UUID clientId) {
        if (!accountRepository.existsByClient_ClientId(clientId)) {
            throw new AccountNotFoundException("Account not found for client ID.");
        }
        return transactionRepository.findAllByAccount_Client_ClientId(clientId);
    }

    @Override
    @DataSourceErrorLoggable
    public List<Transaction> getAllTransactions() {
        return transactionRepository.findAll();
    }

    @Override
    @Transactional
    @DataSourceErrorLoggable
    public Transaction createTransaction(Transaction transaction) {
        UUID clientId = transaction.getAccount().getClient().getClientId();
        Account account = accountRepository.findByClient_ClientId(clientId)
                .orElseThrow(() -> new AccountNotFoundException("Account not found for client ID."));
        transaction.setAccount(account);
        transaction.setTransactionDate(new Date());
        return transactionRepository.save(transaction);
    }

    @Override
    @Transactional
    @DataSourceErrorLoggable
    public void deleteTransaction(Long transactionId) {
        if (!transactionRepository.existsById(transactionId)) {
            throw new TransactionNotFoundException("Transaction not found.");
        }
        transactionRepository.deleteById(transactionId);
    }

    @Override
    @Transactional
    @DataSourceErrorLoggable
    public Transaction updateAmount(Double amount, Long id) {
        Transaction transaction = transactionRepository.findById(id)
                .orElseThrow(() -> new TransactionNotFoundException("Transaction not found."));
        transaction.setAmount(amount);
        return transactionRepository.save(transaction);
    }
}
