package org.example.service.check;

import org.springframework.stereotype.Service;

@Service
public interface Check<T> {
    public boolean getCheck (T check) throws Exception;
}
