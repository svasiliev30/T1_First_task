package org.example.kafka;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Slf4j
@RequiredArgsConstructor
@Component
public class KafkaProducerMetricError<T extends Object> {

    private final KafkaTemplate kafkaTemplate;


    @Value("${spring.kafka.topic.metrics}")
    private String metrics;

    public void send(Object object, String header) throws Exception {
        try {
            Message<String> message = MessageBuilder.withPayload(object.toString())
                    .setHeader(header, UUID.randomUUID().toString()).build();
            log.info("---------------------------");
            log.info("message {}", message.toString());
            kafkaTemplate.send(metrics,
                    UUID.randomUUID().toString(),
                    message).get();
            log.info("---------------------------");
        } catch (Exception e) {
            log.warn("error send in kafka " + object.toString());
            throw new Exception(e);
        }
    }
}
