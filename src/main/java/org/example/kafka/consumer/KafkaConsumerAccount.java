package org.example.kafka.consumer;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.dao.dto.AccountDto;
import org.example.dao.dto.ClientDto;
import org.example.dao.entity.Account;
import org.example.dao.entity.Client;
import org.example.dao.enums.TransactionStatusEnum;
import org.example.dao.repository.AccountRepository;
import org.example.dao.repository.ClientRepository;
import org.example.service.check.CheckStatusAccountImpl;
import org.example.service.mapper.MapperAccount;
import org.example.service.mapper.MapperClient;
import org.example.service.metric.TargetMetric;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Component
public class KafkaConsumerAccount {

    private final ClientRepository clientRepository;

    private final AccountRepository accountRepository;

    @TargetMetric
    @KafkaListener(
            topics = "${spring.kafka.topic.accounts}",
            containerFactory = "kafkaListenerContainerFactoryAccount")
    public void listener(@Payload Account account,
                         Acknowledgment ack,
                         @Header(KafkaHeaders.RECEIVED_TOPIC) String topic,
                         @Header(KafkaHeaders.RECEIVED_KEY) String key) throws Exception {

        log.debug("Account consumer: Обработка новых сообщений");
        try {
            log.info("topic - {}, key - {},", topic, key);

            Optional<Client> clientOpt = clientRepository.findById(account.getClient().getClientId());
            Client client = clientOpt.orElseThrow();
            account.setClient(client);
            accountRepository.save(account);

        } catch (Exception exception) {
            log.error("Ошибка Topic: {}, Key: {}", topic, key);
            throw new Exception("error kafka");
        } finally {
            ack.acknowledge();
        }

        log.debug("Account consumer: записи обработаны");
    }
}
