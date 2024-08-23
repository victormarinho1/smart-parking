package com.fatec.smart_parking.core;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class ApplicationUserService implements UserDetailsService{
    
    @Autowired
    private ApplicationUserRepository applicationUserRepository;

    @Override
    public UserDetails loadUserByUsername(String email) {
        Optional<UserDetails> optionalUser = applicationUserRepository.findByEmail(email);
        if(optionalUser.isPresent()){
            return optionalUser.get();
        }
        throw new UsernameNotFoundException(email);
    }

    public ApplicationUserDTO convertToDTO(ApplicationUser user){
        return new ApplicationUserDTO(user.getId(),user.getName(),user.getEmail(),user.getPassword(),user.getRole());
    }

}
