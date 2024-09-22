package com.fatec.smart_parking.user;

import com.fatec.smart_parking.core.Role;

import com.fatec.smart_parking.core.exception.EmailAlreadyTakenException;
import com.fatec.smart_parking.core.exception.UserNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;


import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Service
public class UserService{

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserValidator userValidator;

    public List<UserDTO> findAll(){
        List<User> clientsList = userRepository.findAll();
        List<UserDTO> clientsDTOList = new  ArrayList();
        for (User user : clientsList) {
            clientsDTOList.add(convertToDTO(user));
        }
        return clientsDTOList;
    }

    public UserDTO findById(Long id){
        User user = userRepository.findById(id)
                .orElseThrow(UserNotFoundException::new);
        return convertToDTO(user);
    }

    public UserDTO findByEmail(String email){
        User user = userRepository.findByEmail(email)
                .orElseThrow(UserNotFoundException::new);
        return convertToDTO(user);
    }

    public UserDTO create(User user) {
        userValidator.checkEmailExists(user.getEmail());
        userValidator.checkEmailValidity(user.getEmail());
        user.setRole(Role.CLIENT);
        user.setPassword(new BCryptPasswordEncoder().encode(user.getPassword()));
        return convertToDTO(userRepository.save(user));
    }

    public UserDTO update(Long id, User user){
        Optional<User> optionalUser = userRepository.findById(id);
        
        if(optionalUser.isPresent()){

            userValidator.checkEmailExists(user.getEmail());
            userValidator.checkEmailValidity(user.getEmail());
            updateData(user, user);
            return convertToDTO(userRepository.save(user));
        }
        throw new UserNotFoundException();
    }

    public void delete(Long id){
        Optional<User> optionalUser = userRepository.findById(id);
        if(optionalUser.isPresent()){
            userRepository.deleteById(id);
        } else {
            throw new UserNotFoundException();
        }
    }

    public UserDTO convertToDTO(User user) {
        return new UserDTO(user.getId(), user.getName(), user.getEmail(), user.getRole(), user.getEnabled());
    }

    public void updateData(User user, User newUser){
        user.setName(newUser.getName());
        user.setEmail(newUser.getEmail());
        user.setPassword(new BCryptPasswordEncoder().encode(newUser.getPassword()));
    }


}
