package org.example.service.generation;

import org.example.kafka.testProduser.KafkaProducerTransaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GenerationServiceTransactionImpl implements GenerationService {

    @Autowired
    KafkaProducerTransaction kafkaProducerTransaction;

    @Override
    public boolean generation() throws Exception {

//        for (long i = 10; i < 15; i++) {
//            Transaction transaction = new Transaction();
//            transaction.setId(i);
//            transaction.setAmountTransaction(new Random().nextDouble(10000));
//            transaction.setTimeTransaction(LocalDateTime.now());
//            kafkaProducerTransaction.send(transaction);
//        }

        return true;
    }
}
