package ru.t1.lint.springaoptask4.kafka;

import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.header.internals.RecordHeader;
import org.apache.kafka.common.header.internals.RecordHeaders;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import ru.t1.lint.springaoptask4.web.dto.TransactionAcceptInfoDTO;

import java.nio.charset.StandardCharsets;
import java.util.UUID;

@RequiredArgsConstructor
@Component
public class KafkaTransactionAcceptInfoProducer<T extends TransactionAcceptInfoDTO> {

    private final KafkaTemplate kafkaTemplate;

    public void send(T message) throws Exception {
        try {
            kafkaTemplate.sendDefault(UUID.randomUUID().toString(), message).get();
        } finally {
            kafkaTemplate.flush();
        }
    }

    public void send(String topic, T message) throws Exception {
        try {
            kafkaTemplate.send(topic, message);
        } finally {
            kafkaTemplate.flush();
        }
    }
}