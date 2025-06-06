package ru.t1.lint.springaoptask4.kafka;

import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;
import ru.t1.lint.springaoptask4.model.Transaction;
import ru.t1.lint.springaoptask4.model.enums.TransactionStatus;
import ru.t1.lint.springaoptask4.service.TransactionService;
import ru.t1.lint.springaoptask4.web.dto.TransactionAcceptInfoDTO;
import ru.t1.lint.springaoptask4.web.dto.TransactionDTO;
import ru.t1.lint.springaoptask4.web.mapper.TransactionMapper;

@RequiredArgsConstructor
@Component
public class KafkaTransactionsConsumer {

    private final TransactionService transactionService;

    private final TransactionMapper transactionMapper;

    private final KafkaTransactionAcceptInfoProducer<TransactionAcceptInfoDTO> kafkaTransactionAcceptInfoProducer;

    @KafkaListener(id = "${t1.kafka.consumer.group-id}",
            topics = {"t1_demo_transactions"})
    public void listener(@Payload TransactionDTO transactionDTO,
                         Acknowledgment ack,
                         @Header(KafkaHeaders.RECEIVED_TOPIC) String topic,
                         @Header(KafkaHeaders.RECEIVED_KEY) String key) throws Exception {
        try {
            Transaction transaction = transactionMapper.toEntity(transactionDTO);
            TransactionAcceptInfoDTO transactionAcceptInfoDTO = transactionService.handleListeningTransaction(transaction);

            if (transactionAcceptInfoDTO != null) {
                kafkaTransactionAcceptInfoProducer.send(
                        "t1_demo_transaction_accept",
                        transactionAcceptInfoDTO
                );
            }
        } finally {
            ack.acknowledge();
        }
    }
}
