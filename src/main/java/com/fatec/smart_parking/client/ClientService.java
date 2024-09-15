package com.fatec.smart_parking.client;

import com.fatec.smart_parking.core.Role;
import com.fatec.smart_parking.core.exception.UserNotFoundException;
import com.fatec.smart_parking.user.User;
import com.fatec.smart_parking.user.UserDTO;
import com.fatec.smart_parking.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ClientService extends UserService{
    @Autowired
    private ClientRepository clientRepository;

    @Override
    public UserDTO findByEmail(String email){
        User client = clientRepository.findByEmailAndRole(email, Role.CLIENT)
                .orElseThrow(UserNotFoundException::new);
        return convertToDTO(client);
    }


}
