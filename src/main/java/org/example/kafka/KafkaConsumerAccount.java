package org.example.kafka;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.dao.dto.AccountDto;
import org.example.service.mapper.Mapper;
import org.example.service.metric.TargetMetric;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Slf4j
@RequiredArgsConstructor
@Component
public class KafkaConsumerAccount {
    @Autowired
    Mapper mapper;

    @TargetMetric
    @KafkaListener(
            topics = "${spring.kafka.topic.accounts}",
            containerFactory = "kafkaListenerContainerFactoryAccount")
    public void listener(@Payload AccountDto accountDto,
//                         Acknowledgment ack,
                         @Header(KafkaHeaders.RECEIVED_TOPIC) String topic,
                         @Header(KafkaHeaders.RECEIVED_KEY) String key) throws Exception {

        log.debug("Account consumer: Обработка новых сообщений");
        try {
            log.info("topic - {}, key - {},", topic, key);
            mapper.mapping(accountDto);

        } catch (Exception exception) {
            log.error("Ошибка Topic: {}, Key: {}", topic, key);
            throw new Exception("error kafka");
        } finally {
//            ack.acknowledge();
        }

        log.debug("Account consumer: записи обработаны");
    }
}
