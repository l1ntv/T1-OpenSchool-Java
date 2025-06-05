package ru.t1.lint.springaoptask3.kafka;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.header.internals.RecordHeader;
import org.apache.kafka.common.header.internals.RecordHeaders;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import ru.t1.lint.springaoptask3.model.DataSourceErrorLog;

import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.UUID;

@Slf4j
@Component
@RequiredArgsConstructor
public class KafkaDataSourceErrorProducer<T extends DataSourceErrorLog> {

    private final KafkaTemplate<String, T> kafkaTemplate;

    public void send(T message) throws Exception {
        try {
            kafkaTemplate.sendDefault(UUID.randomUUID().toString(), message).get();
        } finally {
            kafkaTemplate.flush();
        }
    }

    public void sendTo(String topic, T message) throws Exception {
        try {
            kafkaTemplate.send(topic, message).get();
            kafkaTemplate.send(
                    topic,
                    1,
                    LocalDateTime.now().toEpochSecond(ZoneOffset.of("+03:00")),
                    UUID.randomUUID().toString(),
                    message
            ).get();
        } finally {
            kafkaTemplate.flush();
        }
    }

    public void sendTo(String topic, String headerKey, String headerValue, T message) throws Exception {
        ProducerRecord<String, T> record = new ProducerRecord<>(
                topic,
                null,
                UUID.randomUUID().toString(),
                message,
                new RecordHeaders().add(new RecordHeader(headerKey, headerValue.getBytes(StandardCharsets.UTF_8)))
        );
        kafkaTemplate.send(record).get();
        kafkaTemplate.flush();
    }
}
