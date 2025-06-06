package ru.t1.lint.springaoptask4.kafka;

import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.header.internals.RecordHeader;
import org.apache.kafka.common.header.internals.RecordHeaders;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import ru.t1.lint.springaoptask4.web.dto.TransactionDTO;

import java.nio.charset.StandardCharsets;
import java.util.UUID;

@RequiredArgsConstructor
@Component
public class KafkaTransactionsProducer<T extends TransactionDTO> {

    private final KafkaTemplate kafkaTemplate;

    public void send(T message) throws Exception {
        try {
            kafkaTemplate.sendDefault(UUID.randomUUID().toString(), message).get();
        } finally {
            kafkaTemplate.flush();
        }
    }

    public void sendWithHeader(String topic, String headerKey, String headerValue, T message) throws Exception {
        try {
            ProducerRecord<String, T> record = new ProducerRecord<>(
                    topic,
                    null,
                    UUID.randomUUID().toString(),
                    message,
                    new RecordHeaders().add(new RecordHeader(headerKey, headerValue.getBytes(StandardCharsets.UTF_8)))
            );
            kafkaTemplate.send(record).get();
        } finally {
            kafkaTemplate.flush();
        }
    }
}
