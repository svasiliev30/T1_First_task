package org.example.kafka.testProduser;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.dao.entity.Transaction;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Slf4j
@RequiredArgsConstructor
@Component
public class KafkaProducerTransaction<T extends Object> {

    @Qualifier("kafkaClientTemplate")
    public final KafkaTemplate kafkaTemplate;


    @Value("${spring.kafka.topic.transaction}")
    public String topicName;

    public Object send(Transaction transaction) throws Exception {
        Object result = null;
        try {
            log.info("---------------------------");
            log.info("message {}", transaction.getId());
            result = kafkaTemplate.send(topicName,
                    UUID.randomUUID().toString(),
                    transaction).get();

            log.info("---------------------------");
        } catch (Exception e) {
            log.warn("error send in kafka " + transaction.getId());
            throw new Exception(e);
        }
        return result;
    }
}
