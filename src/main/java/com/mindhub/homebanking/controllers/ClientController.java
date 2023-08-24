package com.mindhub.homebanking.controllers;
import com.mindhub.homebanking.dtos.ClientDTO;
import com.mindhub.homebanking.models.Client;
import com.mindhub.homebanking.repositories.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
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
    @Autowired
    private PasswordEncoder passwordEncoder;
    @RequestMapping(path = "/clients", method = RequestMethod.POST)
    public ResponseEntity<Object> register(@RequestParam String firstName
            , @RequestParam String lastName
            , @RequestParam String email
            , @RequestParam String password
           )
    {
        if (firstName.isBlank() || lastName.isBlank() || email.isBlank() || password.isBlank()) {
            return new ResponseEntity<>("Missing data", HttpStatus.FORBIDDEN);
        }

        if (clientRepository.findByEmail(email) != null) {
            return new ResponseEntity<>("Name already in use", HttpStatus.FORBIDDEN);
        }

        clientRepository.save(new Client(firstName, lastName, email, passwordEncoder.encode(password)));
        return new ResponseEntity<>("Client created", HttpStatus.CREATED);
    }
    @GetMapping("/clients/current")
    public ClientDTO getClientDto(Authentication authentication) {
        return new ClientDTO(clientRepository.findByEmail(authentication.getName()));
    }
    

}
