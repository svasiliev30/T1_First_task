package org.example.kafka.consumer;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.dao.dto.ClientDto;
import org.example.dao.entity.Account;
import org.example.dao.entity.Transaction;
import org.example.dao.enums.TransactionStatusEnum;
import org.example.dao.repository.AccountRepository;
import org.example.dao.repository.TransactionRepository;
import org.example.kafka.produser.KafkaProducerTransactionAccept;
import org.example.service.check.CheckStatusAccountImpl;
import org.example.service.mapper.MapperClient;
import org.example.service.metric.TargetMetric;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Component
public class KafkaConsumerTransaction {
    @Autowired
    AccountRepository accountRepository;
    @Autowired
    TransactionRepository transactionRepository;

    @Autowired
    KafkaProducerTransactionAccept kafkaProduser;


    @TargetMetric
    @Transactional
    @KafkaListener(
            topics = "${spring.kafka.topic.transaction}",
            containerFactory = "kafkaListenerContainerFactoryTransaction")
    public void listener(@Payload Transaction transaction,
                         Acknowledgment ack,
                         @Header(KafkaHeaders.RECEIVED_TOPIC) String topic,
                         @Header(KafkaHeaders.RECEIVED_KEY) String key
    ) throws Exception {
        log.debug("Transaction consumer: Обработка новых сообщений");
        log.info("topic - {}, key - {},", topic, key);
        try {
            Optional<Account> optionalAccount = accountRepository.findByAccountId(transaction.getAccount().getAccountId());
            Account account = optionalAccount.get();

            if (new CheckStatusAccountImpl().getCheck(account)){
                transaction.setTransactionStatusEnum(TransactionStatusEnum.REQUESTED);
                transaction.setAccount(account);
                transactionRepository.save(transaction);
                ClientDto clientDto = new MapperClient().mapping(account, transaction);
                kafkaProduser.send(clientDto);
            }

        } catch (Exception exception) {
            log.error("Ошибка Topic: {}, Key: {}", topic, key);
            throw new Exception("error in consumer");
        } finally {
            ack.acknowledge();
        }

        log.debug("Account consumer: записи обработаны");
    }
}
