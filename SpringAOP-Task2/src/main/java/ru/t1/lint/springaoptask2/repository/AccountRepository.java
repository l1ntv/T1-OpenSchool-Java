package ru.t1.lint.springaoptask1.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.t1.lint.springaoptask1.model.Account;

import java.util.Optional;
import java.util.UUID;

public interface AccountRepository extends JpaRepository<Account, Long> {

    Optional<Account> findByClient_ClientId(UUID clientClientId);

    boolean existsByClient_ClientId(UUID clientClientId);

    void deleteByClient_ClientId(UUID clientClientId);
}
