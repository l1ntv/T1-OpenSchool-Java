package ru.t1.lint.springaoptask1.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.t1.lint.springaoptask1.model.Account;
import ru.t1.lint.springaoptask1.model.exception.AccountNotFoundException;
import ru.t1.lint.springaoptask1.model.exception.ResourceConflictException;
import ru.t1.lint.springaoptask1.repository.AccountRepository;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {

    private final AccountRepository accountRepository;

    @Override
    public Account getAccountByClientId(UUID uuid) {
        return accountRepository.findByClient_ClientId(uuid)
                .orElseThrow(() -> new AccountNotFoundException("Account not found."));
    }

    @Override
    public List<Account> getAllAccounts() {
        return accountRepository.findAll();
    }

    @Override
    public Account createAccount(Account account) {
        UUID clientId = account.getClient().getClientId();
        if (accountRepository.existsByClient_ClientId((clientId))) {
            throw new ResourceConflictException("Account with this client id already exists.");
        }
        return accountRepository.save(account);
    }

    @Override
    public void deleteAccount(UUID id) {
        if (!accountRepository.existsByClient_ClientId(id)) {
            throw new AccountNotFoundException("Account not found for client ID.");
        }
        accountRepository.deleteByClient_ClientId(id);
    }

    @Override
    public Account updateBalance(Double balance, UUID id) {
        if (!accountRepository.existsByClient_ClientId(id)) {
            throw new AccountNotFoundException("Account not found for client ID.");
        }
        Account account = accountRepository.findByClient_ClientId(id).get();
        account.setBalance(balance);
        return accountRepository.save(account);
    }
}
