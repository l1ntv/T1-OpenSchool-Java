package ru.t1.lint.springaoptask4.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.t1.lint.springaoptask4.aop.Cached;
import ru.t1.lint.springaoptask4.aop.DataSourceErrorLoggable;
import ru.t1.lint.springaoptask4.aop.Metric;
import ru.t1.lint.springaoptask4.model.Account;
import ru.t1.lint.springaoptask4.model.Transaction;
import ru.t1.lint.springaoptask4.model.enums.AccountStatus;
import ru.t1.lint.springaoptask4.model.enums.TransactionStatus;
import ru.t1.lint.springaoptask4.model.exception.AccountNotFoundException;
import ru.t1.lint.springaoptask4.model.exception.TransactionNotFoundException;
import ru.t1.lint.springaoptask4.repository.AccountRepository;
import ru.t1.lint.springaoptask4.repository.TransactionRepository;
import ru.t1.lint.springaoptask4.web.dto.TransactionAcceptInfoDTO;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TransactionServiceImpl implements TransactionService {

    private final TransactionRepository transactionRepository;

    private final AccountRepository accountRepository;

    @Override
    @Cached
    @Metric
    @DataSourceErrorLoggable
    public List<Transaction> getAccountTransactions(UUID clientId) {
        if (!accountRepository.existsByClient_ClientId(clientId)) {
            throw new AccountNotFoundException("Account not found for client ID.");
        }
        return transactionRepository.findAllByAccount_Client_ClientId(clientId);
    }

    @Override
    @Cached
    @Metric
    @DataSourceErrorLoggable
    public List<Transaction> getAllTransactions() {
        return transactionRepository.findAll();
    }

    @Override
    @Metric
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
    @Metric
    @Transactional
    @DataSourceErrorLoggable
    public void deleteTransaction(Long transactionId) {
        if (!transactionRepository.existsById(transactionId)) {
            throw new TransactionNotFoundException("Transaction not found.");
        }
        transactionRepository.deleteById(transactionId);
    }

    @Override
    @Metric
    @Transactional
    @DataSourceErrorLoggable
    public Transaction updateAmount(Double amount, Long id) {
        Transaction transaction = transactionRepository.findById(id)
                .orElseThrow(() -> new TransactionNotFoundException("Transaction not found."));
        transaction.setAmount(amount);
        return transactionRepository.save(transaction);
    }

    @Override
    @Metric
    @Transactional
    @DataSourceErrorLoggable
    public TransactionAcceptInfoDTO handleListeningTransaction(Transaction transaction) {
        Account account = transaction.getAccount();
        if (account == null) {
            throw new AccountNotFoundException("Account not found.");
        }

        AccountStatus accountStatus = account.getAccountStatus();
        if (accountStatus.equals(AccountStatus.OPEN)) {
            transaction.setTransactionStatus(TransactionStatus.REQUESTED);
            transactionRepository.save(transaction);
            account.setBalance(account.getBalance() + transaction.getAmount());
            accountRepository.save(account);

            return TransactionAcceptInfoDTO.builder()
                    .clientId(account.getClient().getClientId())
                    .accountId(account.getId())
                    .transactionId(transaction.getId())
                    .transactionDate(transaction.getTransactionDate())
                    .transactionAmount(transaction.getAmount())
                    .accountBalance(account.getBalance())
                    .build();
        }
        return null;
    }
}
