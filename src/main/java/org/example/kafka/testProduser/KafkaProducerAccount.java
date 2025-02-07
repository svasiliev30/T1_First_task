package org.example.kafka.testProduser;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.dao.entity.Account;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Slf4j
@RequiredArgsConstructor
@Component
public class KafkaProducerAccount<T extends Object> {

    private final KafkaTemplate kafkaTemplate;

    @Value("${spring.kafka.topic.accounts}")
    private String topicName;

    public Object send(Account account) throws Exception {
        Object result = null;
        try {
            log.info("---------------------------");
            log.info("message {}", account.getId());
            result = kafkaTemplate.send(topicName,
                    UUID.randomUUID().toString(),
                    account).get();

            log.info("---------------------------");
        } catch (Exception e) {
            log.warn("error send in kafka " + account.getId());
            throw new Exception(e);
        }
        return result;
    }
}
