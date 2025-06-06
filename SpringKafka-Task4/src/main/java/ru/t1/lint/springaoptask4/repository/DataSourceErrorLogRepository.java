package ru.t1.lint.springaoptask4.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.t1.lint.springaoptask4.model.DataSourceErrorLog;

public interface DataSourceErrorLogRepository extends JpaRepository<DataSourceErrorLog, Long> {

}
