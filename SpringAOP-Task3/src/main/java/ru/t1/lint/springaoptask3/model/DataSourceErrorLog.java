package ru.t1.lint.springaoptask3.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;

@Entity(name = "data_source_error_logs")
@Table
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class DataSourceErrorLog extends AbstractEntity {

    @Column(name = "stacktrace_text")
    private String stacktraceText;

    @Column(name = "message")
    private String message;

    @Column(name = "method_signature")
    private String methodSignature;
}
