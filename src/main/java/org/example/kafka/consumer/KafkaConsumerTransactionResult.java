package org.example.kafka.consumer;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.dao.dto.ClientDto;
import org.example.dao.dto.TransactionDto;
import org.example.dao.entity.Account;
import org.example.dao.entity.Transaction;
import org.example.dao.enums.TransactionStatusEnum;
import org.example.dao.repository.AccountRepository;
import org.example.dao.repository.TransactionRepository;
import org.example.kafka.produser.KafkaProducerTransactionResult;
import org.example.service.check.CheckStatus;
import org.example.service.check.CheckTimeImpl;
import org.example.service.metric.TargetMetric;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Slf4j
@Component
public class KafkaConsumerTransactionResult {


    private final CheckStatus checkStatus;

    @Autowired
    public KafkaConsumerTransactionResult(@Qualifier("CheckTransactionResultImpl")CheckStatus checkStatus) {
        this.checkStatus = checkStatus;
    }

    @TargetMetric
    @Transactional
    @KafkaListener(
            topics = "${spring.kafka.topic.transactionResult}",
            containerFactory = "kafkaListenerContainerFactoryTransactionDto")
    public void listener(@Payload TransactionDto transactionDto,
                         Acknowledgment ack,
                         @Header(KafkaHeaders.RECEIVED_TOPIC) String topic,
                         @Header(KafkaHeaders.RECEIVED_KEY) String key
    ) throws Exception {
        log.debug("TransactionResul consumer: Обработка новых сообщений");
        log.info("topic - {}, key - {},", topic, key);
        try {
            checkStatus.getCheck(transactionDto);

        } catch (Exception exception) {
            log.error("Ошибка Topic: {}, Key: {}", topic, key);
            throw new Exception("error in consumer");
        } finally {
            ack.acknowledge();
        }

        log.debug("TransactionResul consumer: записи обработаны");
    }

}
