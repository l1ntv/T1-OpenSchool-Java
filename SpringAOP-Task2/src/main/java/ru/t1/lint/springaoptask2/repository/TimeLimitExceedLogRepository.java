package ru.t1.lint.springaoptask2.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.t1.lint.springaoptask2.model.TimeLimitExceedLog;

@Repository
public interface TimeLimitExceedLogRepository extends JpaRepository<TimeLimitExceedLog, Long> {
}
