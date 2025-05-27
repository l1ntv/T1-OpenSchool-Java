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
public class DataMetricErrorLog extends AbstractEntity {

    @Column(name = "stacktrace_text")
    private String stacktraceText;

    @Column(name = "message")
    private String message;

    @Column(name = "method_signature")
    private String methodSignature;
}
