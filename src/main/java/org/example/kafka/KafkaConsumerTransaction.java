package org.example.kafka;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.dao.entity.Transaction;
import org.example.dao.repository.TransactionRepository;
import org.example.service.metric.TargetMetric;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@RequiredArgsConstructor
@Component
public class KafkaConsumerTransaction {
    @Autowired
    TransactionRepository repository;

    @TargetMetric
    @Transactional
    @KafkaListener(
            topics = "${spring.kafka.topic.transaction}",
            containerFactory = "kafkaListenerContainerFactoryTransaction")
    public void listener(@Payload Transaction transaction,
//                         Acknowledgment ack,
                         @Header(KafkaHeaders.RECEIVED_TOPIC) String topic,
                         @Header(KafkaHeaders.RECEIVED_KEY) String key
    ) throws Exception {

        log.debug("Account consumer: Обработка новых сообщений");
        try {
            log.info("topic - {}, key - {},", topic, key);
            repository.save(transaction);
        } catch (Exception exception) {
            log.error("Ошибка Topic: {}, Key: {}", topic, key);
            throw new Exception("error kafka");
        } finally {
//            ack.acknowledge();
        }

        log.debug("Account consumer: записи обработаны");
    }
}
