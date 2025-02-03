package org.example.service.check;

import lombok.extern.slf4j.Slf4j;
import org.example.dao.dto.ClientDto;
import org.example.dao.dto.TransactionDto;
import org.example.dao.enums.TransactionStatusEnum;
import org.example.kafka.produser.KafkaProducerTransactionResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Slf4j
@Service("CheckTransactionAcceptImpl")
public class CheckTransactionAcceptImpl implements CheckStatus<ClientDto> {

    @Autowired
    KafkaProducerTransactionResult kafkaProducer;

    @Autowired
    @Qualifier("CheckTimeImpl")
    Check check;

    @Override
    public void getCheck(ClientDto clientDto) throws Exception {
        Long transactionId = clientDto.getTransactionId();
        Long accountId = clientDto.getAccountId();
        TransactionDto transactionDto = new TransactionDto();
        try {
            log.info("Check transaction - {}", clientDto.toString());
            transactionDto.setTransactionId(transactionId);
            transactionDto.setAccountId(accountId);

            boolean status = check.getCheck(clientDto);
            if (status) {
                if (clientDto.getAccountBalance() > 0) {
                    transactionDto.setTransactionStatusEnum(TransactionStatusEnum.ACCEPTED.toString());
                } else {
                    transactionDto.setTransactionStatusEnum(TransactionStatusEnum.REJECTED.toString());
                }
            } else {
                transactionDto.setTransactionStatusEnum(TransactionStatusEnum.BLOCKED.toString());
            }
            log.info("Result Check transaction - {}", transactionDto.getTransactionStatusEnum().toString());
            kafkaProducer.send(transactionDto);
        } catch (Exception e) {
            log.error("Error check transaction client with transaction id - {} and number account - {}", transactionId, accountId);
            throw new Exception();
        }
    }
}
