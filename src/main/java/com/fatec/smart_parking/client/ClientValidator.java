package com.fatec.smart_parking.client;

import com.fatec.smart_parking.core.ApplicationUserValidator;
import com.fatec.smart_parking.core.exception.DocumentAlreadyTakenException;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class ClientValidator extends ApplicationUserValidator {
    @Autowired
    private ClientRepository clientRepository;


    public void checkDocumentExists(String email) {
        if(clientRepository.existsByDocument(email)){
            throw new DocumentAlreadyTakenException();
        }
    }
}
