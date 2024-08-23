package com.fatec.smart_parking.client;

import com.fatec.smart_parking.core.exception.ClientNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ClientService {

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private ClientValidator clientValidator;

    public List<ClientDTO> findAll(){
        List<Client> list = clientRepository.findAll();
        return list.stream().map(this::convertToDTO).collect(Collectors.toList());
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

    public ClientDTO create(ClientDTO clientDTO) {
        clientValidator.checkEmailExists(clientDTO.email());
        clientValidator.checkEmailValidity(clientDTO.email());

        Client client =  convertToClient(clientDTO);
        client.setPassword(new BCryptPasswordEncoder().encode(client.getPassword()));

        return convertToDTO(clientRepository.save(client));
    }

    public ClientDTO update(Long id, ClientDTO clientDTO){
        Optional<Client> optionalClient = clientRepository.findById(id);
        
        if(optionalClient.isPresent()){   
            Client client = optionalClient.get();
            clientValidator.checkEmailExists(clientDTO.email());
            clientValidator.checkEmailValidity(clientDTO.email());         
            updateData(client, clientDTO);
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

    public Client convertToClient(ClientDTO clientDTO) {
        return new Client(clientDTO.id(),clientDTO.name(),clientDTO.email(),clientDTO.password());
    }
    public ClientDTO convertToDTO(Client client) {
        return new ClientDTO(client.getId(), client.getName(),client.getEmail(),client.getPassword());
    }

    public void updateData(Client client, ClientDTO newClient){
        client.setName(newClient.name());
        client.setEmail(newClient.email());
        client.setPassword(new BCryptPasswordEncoder().encode(newClient.password()));
    }
}
