package ru.t1.lint.springaoptask3.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.t1.lint.springaoptask3.model.Transaction;

import java.util.List;
import java.util.UUID;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {

    List<Transaction> findAllByAccount_Client_ClientId(UUID accountClientClientId);
}
