package com.fatec.smart_parking.client;

import com.fatec.smart_parking.core.ApplicationUserService;
import com.fatec.smart_parking.core.Role;
import com.fatec.smart_parking.core.exception.ClientNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;


import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Service
public class ClientService extends ApplicationUserService {

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private ClientValidator clientValidator;

    public List<ClientDTO> findAll(){
        List<Client> clientsList = clientRepository.findAll();
        List<ClientDTO> clientsDTOList = new  ArrayList();
        for (Client client : clientsList) {
            clientsDTOList.add(convertToDTO(client));
        }
        return clientsDTOList;
    }

    public ClientDTO findById(Long id){
        Client client = clientRepository.findById(id)
                .orElseThrow(ClientNotFoundException::new);
        return convertToDTO(client);
    }

    public ClientDTO findByEmail(String email){
        Client client = clientRepository.findByEmail(email)
                .orElseThrow(ClientNotFoundException::new);
        return convertToDTO(client);
    }

    public ClientDTO create(Client client) {
        clientValidator.checkEmailExists(client.getEmail());
        clientValidator.checkEmailValidity(client.getEmail());
        clientValidator.checkDocumentExists(client.getDocument());
        client.setRole(Role.CLIENT);
        client.setPassword(new BCryptPasswordEncoder().encode(client.getPassword()));
        return convertToDTO(clientRepository.save(client));
    }

    public ClientDTO update(Long id, Client client){
        Optional<Client> optionalClient = clientRepository.findById(id);
        
        if(optionalClient.isPresent()){   

            clientValidator.checkEmailExists(client.getEmail());
            clientValidator.checkEmailValidity(client.getEmail());
            updateData(client, client);
            return convertToDTO(clientRepository.save(client));
        }
        throw new ClientNotFoundException();
    }

    public void delete(Long id){
        Optional<Client> optionalClient = clientRepository.findById(id);
        if(optionalClient.isPresent()){
            clientRepository.deleteById(id);
        } else {
            throw new ClientNotFoundException();
        }
    }

    public ClientDTO convertToDTO(Client client) {
        return new ClientDTO(client.getId(), client.getName(),client.getDocument(),client.getEmail(),client.getRole(),client.getEnabled());
    }

    public void updateData(Client client, Client newClient){
        client.setName(newClient.getName());
        client.setEmail(newClient.getEmail());
        client.setPassword(new BCryptPasswordEncoder().encode(newClient.getPassword()));
    }
}
