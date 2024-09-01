package com.fatec.smart_parking.core;

import com.fatec.smart_parking.core.exception.EmailAlreadyTakenException;
import com.fatec.smart_parking.core.exception.EmailNotValidException;
import com.fatec.smart_parking.core.validation.EmailValidator;
import org.springframework.beans.factory.annotation.Autowired;


public class ApplicationUserValidator{
    @Autowired
    ApplicationUserRepository applicationUserRepository;

    @Autowired
    private EmailValidator emailValidator;

    public void checkEmailExists(String email) {
        if(applicationUserRepository.existsByEmail(email)){
            throw new EmailAlreadyTakenException();
        }
    }

    public void checkEmailValidity(String email) {
        if (!(emailValidator.checkMailPattern(email))) {
            throw new EmailNotValidException();
        }
    }
}
