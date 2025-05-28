package ru.t1.lint.springaoptask2.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.t1.lint.springaoptask2.aop.Cached;
import ru.t1.lint.springaoptask2.aop.DataSourceErrorLoggable;
import ru.t1.lint.springaoptask2.aop.Metric;
import ru.t1.lint.springaoptask2.model.Account;
import ru.t1.lint.springaoptask2.model.Client;
import ru.t1.lint.springaoptask2.model.exception.AccountNotFoundException;
import ru.t1.lint.springaoptask2.model.exception.ClientNotFoundException;
import ru.t1.lint.springaoptask2.model.exception.ResourceConflictException;
import ru.t1.lint.springaoptask2.repository.AccountRepository;
import ru.t1.lint.springaoptask2.repository.ClientRepository;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {

    private final AccountRepository accountRepository;

    private final ClientRepository clientRepository;

    @Override
    @Cached
    @Metric
    @DataSourceErrorLoggable
    public Account getAccountByClientId(UUID uuid) {
        return accountRepository.findByClient_ClientId(uuid)
                .orElseThrow(() -> new AccountNotFoundException("Account not found."));
    }

    @Override
    @Cached
    @Metric
    @DataSourceErrorLoggable
    public List<Account> getAllAccounts() {
        return accountRepository.findAll();
    }

    @Override
    @Cached
    @Metric
    @Transactional
    @DataSourceErrorLoggable
    public Account createAccount(Account account) {
        UUID clientId = account.getClient().getClientId();
        Client client = clientRepository.findByClientId(clientId)
                .orElseThrow(() -> new ClientNotFoundException("Client not found."));
        if (accountRepository.existsByClient_ClientId((clientId))) {
            throw new ResourceConflictException("Account with this client id already exists.");
        }
        account.setClient(client);
        return accountRepository.save(account);
    }

    @Override
    @Cached
    @Metric
    @Transactional
    @DataSourceErrorLoggable
    public void deleteAccount(UUID id) {
        if (!accountRepository.existsByClient_ClientId(id)) {
            throw new AccountNotFoundException("Account not found for client ID.");
        }
        accountRepository.deleteByClient_ClientId(id);
    }

    @Override
    @Cached
    @Metric
    @Transactional
    @DataSourceErrorLoggable
    public Account updateBalance(Double balance, UUID id) {
        if (!accountRepository.existsByClient_ClientId(id)) {
            throw new AccountNotFoundException("Account not found for client ID.");
        }
        Account account = accountRepository.findByClient_ClientId(id).get();
        account.setBalance(balance);
        return accountRepository.save(account);
    }
}
