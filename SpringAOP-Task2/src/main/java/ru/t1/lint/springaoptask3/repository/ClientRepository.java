package ru.t1.lint.springaoptask3.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.t1.lint.springaoptask3.model.Client;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface ClientRepository extends JpaRepository<Client, Long> {

    Optional<Client> findByClientId(UUID clientId);

    boolean existsByClientId(UUID clientId);

    void deleteByClientId(UUID clientId);
}
