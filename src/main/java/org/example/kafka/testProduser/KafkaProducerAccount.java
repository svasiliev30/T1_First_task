package org.example.kafka.testProduser;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.dao.dto.AccountDto;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Slf4j
@RequiredArgsConstructor
@Component
public class KafkaProducerAccount<T extends Object> {

    @Qualifier("kafkaClientTemplate")
    public final KafkaTemplate kafkaTemplate;

    @Value("${spring.kafka.topic.accounts}")
    public String topicName;

    public Object send(AccountDto accountDto) throws Exception {
        Object result = null;
        try {
            log.info("---------------------------");
            log.info("message {}", accountDto.getId());
            result = kafkaTemplate.send(topicName,
                    UUID.randomUUID().toString(),
                    accountDto).get();

            log.info("---------------------------");
        } catch (Exception e) {
            log.warn("error send in kafka " + accountDto.getId());
            throw new Exception(e);
        }
        return result;
    }
}
