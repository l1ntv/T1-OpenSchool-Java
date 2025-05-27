package ru.t1.lint.springaoptask2.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;

@Entity(name = "time_limit_exceed_log")
@Table
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class TimeLimitExceedLog extends AbstractEntity {

    @Column(name = "method_signature")
    private String methodSignature;

    @Column(name = "execution_time_in_ms")
    private Long executionTime;

    @Column(name = "max_execution_time_in_ms")
    private Long maxExecutionTime;
}
