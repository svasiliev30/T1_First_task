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

@RestController
@RequestMapping(value = "/generation")
@RequiredArgsConstructor
public class GenerationController {

    private final GenerationServiceAccountImpl generationServiceAc;

    private final GenerationServiceTransactionImpl generationServiceTransaction;

    private final GenerationServiceClientsImpl generationServiceClients;


    @GetMapping("/accountGeneration")
    public ResponseEntity<Object> createAccount() throws Exception {
        return new ResponseEntity<>(generationServiceAc.generation(), HttpStatus.OK) ;
    }

    @GetMapping("/transactionGeneration")
    public ResponseEntity<Object> createTransaction() throws Exception {
        return new ResponseEntity<>(generationServiceTransaction.generation(), HttpStatus.OK) ;
    }

    @GetMapping("/generationServiceClients")
    public ResponseEntity<Object> createClient() throws Exception {
        return new ResponseEntity<>(generationServiceClients.generation(), HttpStatus.OK) ;
    }
}
