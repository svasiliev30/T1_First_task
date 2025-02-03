package org.example.kafka.consumer;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.dao.dto.ClientDto;
import org.example.service.check.CheckStatus;
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

@Slf4j
@RequiredArgsConstructor
@Component
public class KafkaConsumerTransactionAccept {

    @Autowired
    @Qualifier("CheckTransactionAcceptImpl")
    CheckStatus checkStatus;

    @TargetMetric
    @Transactional
    @KafkaListener(
            topics = "${spring.kafka.topic.transactionAccept}",
            containerFactory = "kafkaListenerContainerFactoryClientDto")
    public void listener(@Payload ClientDto clientDto,
                         Acknowledgment ack,
                         @Header(KafkaHeaders.RECEIVED_TOPIC) String topic,
                         @Header(KafkaHeaders.RECEIVED_KEY) String key
    ) throws Exception {
        log.debug("TransactionAccept consumer: Обработка новых сообщений");
        log.info("topic - {}, key - {},", topic, key);
        try {
            checkStatus.getCheck(clientDto);
        } catch (Exception exception) {
            log.error("Ошибка Topic: {}, Key: {}", topic, key);
            throw new Exception("error in consumer");
        } finally {
            ack.acknowledge();
        }

        log.debug("TransactionAccept consumer: записи обработаны");
    }


}
