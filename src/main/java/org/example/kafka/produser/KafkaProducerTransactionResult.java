package org.example.kafka.produser;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.dao.dto.TransactionDto;
import org.example.dao.entity.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Slf4j
@RequiredArgsConstructor
@Component
public class KafkaProducerTransactionResult<T extends Object> {


    private final KafkaTemplate kafkaTemplate;

    @Value("${spring.kafka.topic.transactionResult}")
    public String topicName;

    public Object send(TransactionDto transactionDto) throws Exception {
        Object result = null;
        try {
            log.info("---------------------------");
            log.info("message {}", transactionDto.getTransactionId());
            result = kafkaTemplate.send(topicName,
                    UUID.randomUUID().toString(),
                    transactionDto).get();

            log.info("---------------------------");
        } catch (Exception e) {
            log.warn("error send in kafka " + transactionDto.getTransactionId());
            throw new Exception(e);
        }
        return result;
    }
}
