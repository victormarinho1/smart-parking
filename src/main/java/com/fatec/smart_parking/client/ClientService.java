package com.fatec.smart_parking.client;

import com.fatec.smart_parking.core.exception.ClientNotFoundException;
import com.fatec.smart_parking.core.exception.EmailAlreadyTakenException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ClientService {

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private ClientValidator clientValidator;

    public List<ClientDTO> findAll(){
        List<Client> list = clientRepository.findAll();
        List<ClientDTO> listDto = list.stream().map(client -> new ClientDTO(client.getId(),client.getName(), client.getEmail() ,client.getPassword())).collect(Collectors.toList());
        return listDto;
    }

    public ClientDTO findById(Long id){
        Client client = clientRepository.findById(id)
                .orElseThrow(() -> new ClientNotFoundException());
        return convertToDTO(client);
    }

    public ClientDTO findByEmail(String email){
        Client client = clientRepository.findByEmail(email)
                .orElseThrow(() -> new ClientNotFoundException());
        return convertToDTO(client);
    }

    public Client create(ClientDTO clientDTO) {;
        clientValidator.checkEmailExists(clientDTO.email());
        clientValidator.checkEmailValidity(clientDTO.email());
        return clientRepository.save(convertToClient(clientDTO));
    }

    public Client convertToClient(ClientDTO clientDTO) {
        return new Client(clientDTO.id(),clientDTO.name(),clientDTO.email(),clientDTO.password());
    }
    public ClientDTO convertToDTO(Client client) {
        return new ClientDTO(client.getId(), client.getName(),client.getEmail(),client.getPassword());
    }
}
