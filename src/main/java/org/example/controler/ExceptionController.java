package org.example.controler;

import lombok.RequiredArgsConstructor;
import org.example.dao.entity.DataSourceErrorLog;
import org.example.dao.repository.DataSourceErrorLogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
@RequiredArgsConstructor
public class ExceptionController {

    private  final DataSourceErrorLog dataSourceErrorLog;

    @Transactional
    @ExceptionHandler(Exception.class)
    public ResponseEntity<DataSourceErrorLog> handleException() {
        return new ResponseEntity<>(dataSourceErrorLog, HttpStatus.BAD_REQUEST);
    }
}
