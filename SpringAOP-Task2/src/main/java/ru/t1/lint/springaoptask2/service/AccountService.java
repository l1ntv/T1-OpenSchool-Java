package ru.t1.lint.springaoptask1.service;

import ru.t1.lint.springaoptask1.model.Account;

import java.util.List;
import java.util.UUID;

public interface AccountService {

    Account getAccountByClientId(UUID id);

    List<Account> getAllAccounts();

    Account createAccount(Account account);

    void deleteAccount(UUID id);

    Account updateBalance(Double balance, UUID id);
}
