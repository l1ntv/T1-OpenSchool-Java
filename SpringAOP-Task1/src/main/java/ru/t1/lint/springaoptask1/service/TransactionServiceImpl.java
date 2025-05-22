package ru.t1.lint.springaoptask1.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
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
    public List<Transaction> getAccountTransactions(UUID clientId) {
        if (!accountRepository.existsByClient_ClientId(clientId)) {
            throw new AccountNotFoundException("Account not found for client ID.");
        }
        return transactionRepository.findAllByAccount_Client_ClientId(clientId);
    }

    @Override
    public List<Transaction> getAllTransactions() {
        return transactionRepository.findAll();
    }

    @Override
    public Transaction createTransaction(Transaction transaction) {
        UUID transactionId = transaction.getAccount().getClient().getClientId();
        if (!accountRepository.existsByClient_ClientId(transactionId)) {
            throw new AccountNotFoundException("Account not found for client ID.");
        }
        transaction.setTransactionDate(new Date());
        return transactionRepository.save(transaction);
    }

    @Override
    public void deleteTransaction(Long transactionId) {
        if (!transactionRepository.existsById(transactionId)) {
            throw new TransactionNotFoundException("Transaction not found.");
        }
        transactionRepository.deleteById(transactionId);
    }

    @Override
    public Transaction updateAmount(Double amount, Long id) {
        if (!transactionRepository.existsById(id)) {
            throw new TransactionNotFoundException("Transaction not found.");
        }
        Transaction transaction = transactionRepository.findById(id).get();
        transaction.setAmount(amount);
        return transactionRepository.save(transaction);
    }
}
