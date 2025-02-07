package org.example.service.check;

import org.springframework.stereotype.Service;

@Service
public interface CheckStatus <T> {
    public void getCheck (T check) throws Exception;
}
