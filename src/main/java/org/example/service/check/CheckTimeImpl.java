package org.example.service.check;

import lombok.extern.slf4j.Slf4j;
import org.example.dao.dto.ClientDto;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Service("CheckTimeImpl")
public class CheckTimeImpl implements Check<ClientDto> {

    private static final int TRANSACTION_LIMIT = 2;
    private static final long TIME_STAMP_WINDOW = 600000000;


    private Map<String, List<Long>> cashTransactions = new HashMap<>();

    @Override
    public boolean getCheck(ClientDto clientDto) throws Exception {
        long clientId = clientDto.getClientId();
        long accountId = clientDto.getAccountId();
        log.info("Check time client with id - {} and number account - {}", clientId, accountId);
        String key = clientId + "/" + accountId;
        long timestamp = clientDto.getTimestamp().atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
        try {
            List<Long> timeStampList = cashTransactions.getOrDefault(key, new ArrayList<>());
            timeStampList.add(timestamp);

            long timeNow = Instant.now().toEpochMilli();
            timeStampList.removeIf(time -> (timeNow - time > TIME_STAMP_WINDOW));
            cashTransactions.put(key, timeStampList);
            if (timeStampList.size() > TRANSACTION_LIMIT) {
                log.info("Status transaction with client id - {} and number account - {} is BLOCKED", clientId, accountId);
                return false;
            }
        } catch (Exception e) {
            log.error("Error check time client with id - {} and number account - {}", clientId, accountId);
            throw new Exception();
        }
        log.info("Status transaction with client id - {} and number account - {} is OPEN", clientId, accountId);
        return true;
    }

}
