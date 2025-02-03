package org.example.controler;

import lombok.RequiredArgsConstructor;
import org.example.service.generation.GenerationServiceAccountImpl;
import org.example.service.generation.GenerationServiceClientsImpl;
import org.example.service.generation.GenerationServiceTransactionImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/generation")
public class GenerationController {

    @Autowired
    GenerationServiceAccountImpl generationServiceAc;

    @Autowired
    GenerationServiceTransactionImpl generationServiceTransaction;

    @Autowired
    GenerationServiceClientsImpl generationServiceClients;

    @GetMapping("/accountGeneration")
    public ResponseEntity<Object> createAccount() throws Exception {

        boolean result = generationServiceAc.generation();
        return new ResponseEntity<>(result, HttpStatus.OK) ;
    }

    @GetMapping("/transactionGeneration")
    public ResponseEntity<Object> createTransaction() throws Exception {

        boolean result = generationServiceTransaction.generation();
        return new ResponseEntity<>(result, HttpStatus.OK) ;
    }

    @GetMapping("/generationServiceClients")
    public ResponseEntity<Object> createClient() throws Exception {

        boolean result = generationServiceClients.generation();
        return new ResponseEntity<>(result, HttpStatus.OK) ;
    }
}
