package com.fatec.smart_parking.core;

import java.util.Optional;

import com.fatec.smart_parking.core.email.EmailService;
import com.fatec.smart_parking.user.User;
import com.fatec.smart_parking.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class ApplicationUserService implements UserDetailsService{
    
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private EmailService emailService;

    @Override
    public User loadUserByUsername(String email) {
        Optional<User> optionalUser = userRepository.findByEmail(email);
        if(optionalUser.isPresent()){
            return optionalUser.get();
        }
        throw new UsernameNotFoundException(email);
    }


    public void resetPassword(String email){
        this.emailService.sendSimpleMessage(email,"Reset Password","Your new password has been reset");
    }



}
