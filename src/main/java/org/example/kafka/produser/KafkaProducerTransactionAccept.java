package org.example.kafka.produser;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.dao.dto.AccountDto;
import org.example.dao.dto.ClientDto;
import org.example.dao.entity.Account;
import org.example.dao.entity.Transaction;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Slf4j
@RequiredArgsConstructor
@Component
public class KafkaProducerTransactionAccept<T extends Object> {

    @Qualifier("kafkaClientTemplate")
    public final KafkaTemplate kafkaTemplate;


    @Value("${spring.kafka.topic.transactionAccept}")
    public String topicName;

    public Object send(ClientDto clientDto) throws Exception {
        Object result = null;
        try {
            log.info("---------------------------");
            log.info("message {}", clientDto.toString());
            result = kafkaTemplate.send(topicName,
                    UUID.randomUUID().toString(),
                    clientDto).get();

            log.info("---------------------------");
        } catch (Exception e) {
            log.warn("error send in kafka topic - {} , {}",topicName, clientDto.toString());
            log.info("---------------------------");
            throw new Exception(e);
        }
        return result;
    }
}
