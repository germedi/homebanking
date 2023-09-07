package com.mindhub.homebanking.services.implement;

import com.mindhub.homebanking.dtos.ClientDTO;
import com.mindhub.homebanking.models.Client;
import com.mindhub.homebanking.repositories.ClientRepository;
import com.mindhub.homebanking.services.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import javax.persistence.Tuple;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ClientServiceImplement implements ClientService {

    @Autowired // Inyecta un objeto ClientRepository
    private ClientRepository clientRepository;
    @Override
    public List<ClientDTO> getClients() {
        return  clientRepository.findAll().stream()
                .map(currentClient -> new ClientDTO(currentClient))
                .collect(Collectors.toList()); // Devuelve la lista de objetos ClientDTO;
    }

    @Override
    public ClientDTO getClientById(long id) {
        // Obtiene un objeto Optional<Client> del repositorio clientRepository utilizando el id
        return new ClientDTO(clientRepository.findById(id).orElse(null));     // Crea un objeto ClientDTO a partir del objeto Client obtenido anteriormente y lo devuelve

    }
    @Override
    public ClientDTO getClientDto(Authentication authentication){
        return new ClientDTO(clientRepository.findByEmail(authentication.getName()));
    }



    @Override
    public Client getClientByEmail(String email) {
        return clientRepository.findByEmail(email);
    }

    @Override
    public void saveClient(Client client) {
        clientRepository.save(client);

    }

    @Override
    public Tuple findByEmailIgnoreCase(String name,Authentication authentication) {
        return (Tuple) clientRepository.findByEmailIgnoreCase(authentication.getName()).get();
    }
}
