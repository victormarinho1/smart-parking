package com.fatec.smart_parking.core;

import java.util.Optional;

import com.fatec.smart_parking.core.exception.EmailAlreadyTakenException;
import com.fatec.smart_parking.user.User;
import com.fatec.smart_parking.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class ApplicationUserService implements UserDetailsService{
    
    @Autowired
    private UserRepository userRepository;

    @Override
    public User loadUserByUsername(String email) {
        Optional<User> optionalUser = userRepository.findByEmail(email);
        if(optionalUser.isPresent()){
            return optionalUser.get();
        }
        throw new UsernameNotFoundException(email);
    }




}
