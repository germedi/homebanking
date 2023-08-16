package com.mindhub.homebanking.controllers;

import com.mindhub.homebanking.dtos.ClientDTO;
import com.mindhub.homebanking.repositories.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class ClientController {
    @Autowired // Inyecta un objeto ClientRepository
    private ClientRepository clientRepository;

    @GetMapping("/clients")   // Mapea la URL "/api/clients" a este método y devuelve una lista de objetos ClientDTO
    public List<ClientDTO> getClients() {

        return clientRepository.findAll().stream()
                .map(currentClient -> new ClientDTO(currentClient))
                .collect(Collectors.toList()); // Devuelve la lista de objetos ClientDTO
    }

    @GetMapping("/clients/{id}")  // Mapea la URL "/api/clients/{id}" a este método y devuelve un objeto ClientDTO con el id
    public ClientDTO getClientById(@PathVariable Long id) {

        // Obtiene un objeto Optional<Client> del repositorio clientRepository utilizando el id
       return new ClientDTO(clientRepository.findById(id).orElse(null));     // Crea un objeto ClientDTO a partir del objeto Client obtenido anteriormente y lo devuelve
    }

}
