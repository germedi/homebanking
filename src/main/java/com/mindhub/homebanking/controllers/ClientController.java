package com.mindhub.homebanking.controllers;
import com.mindhub.homebanking.dtos.ClientDTO;
import com.mindhub.homebanking.models.Client;
import com.mindhub.homebanking.services.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import java.util.List;


@RestController
@RequestMapping("/api")
public class ClientController {

    @Autowired
    private AccountController accountController;

    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private ClientService clientService;

    @GetMapping("/clients")   // Mapea la URL "/api/clients" a este método y devuelve una lista de objetos ClientDTO
    public List<ClientDTO> getClients() {

        return clientService.getClients(); // Devuelve la lista de objetos ClientDTO
    }

    @GetMapping("/clients/{id}")  // Mapea la URL "/api/clients/{id}" a este método y devuelve un objeto ClientDTO con el id
    public ClientDTO getClientById(@PathVariable Long id) {

        return clientService.getClientById(id);
    }

    @PostMapping("/clients")
    public ResponseEntity<Object> register(@RequestParam String firstName
            , @RequestParam String lastName
            , @RequestParam String email
            , @RequestParam String password
    )
    {
        if (firstName.isBlank() || lastName.isBlank() || email.isBlank() || password.isBlank()) {
            return new ResponseEntity<>("Missing data", HttpStatus.BAD_REQUEST);
        }

        if (clientService.getClientByEmail(email) != null) {
            return new ResponseEntity<>("Email already in use", HttpStatus.CONFLICT);
        }

        // Crear nuevo cliente y guardarlo en la base de datos
        Client newClient = new Client(firstName, lastName, email, passwordEncoder.encode(password));
        clientService.saveClient(newClient);

        // Crear nueva cuenta para el cliente
        accountController.createAccount(new UsernamePasswordAuthenticationToken(newClient.getEmail(), newClient.getPassword()));

        return new ResponseEntity<>("Client created", HttpStatus.CREATED);
    }


    @GetMapping("/clients/current")
    public ClientDTO getClientDto(Authentication authentication) {
        return clientService.getClientDto(authentication);
    }

    @PutMapping("/clients/current")
    public ResponseEntity<Object> updateClient(Authentication authentication, @RequestBody ClientDTO clientDTO) {
        Client client = clientService.getClientByEmail(authentication.getName());

        if (client == null) {
            return new ResponseEntity<>("Client not found", HttpStatus.NOT_FOUND);
        }

        if (clientDTO.getFirstName() == null || clientDTO.getLastName() == null || clientDTO.getEmail() == null) {
            return new ResponseEntity<>("Missing data", HttpStatus.BAD_REQUEST);
        }

        if (!client.getEmail().equals(authentication.getName())) {
            return new ResponseEntity<>("Unauthorized", HttpStatus.UNAUTHORIZED);
        }

        // Actualizar la información del cliente
        client.setFirstName(clientDTO.getFirstName());
        client.setLastName(clientDTO.getLastName());
        client.setEmail(clientDTO.getEmail());

        boolean passwordUpdated = false;
        if (clientDTO.getPassword() != null && !clientDTO.getPassword().isEmpty()) {
            client.setPassword(passwordEncoder.encode(clientDTO.getPassword()));
            passwordUpdated = true;
        }

        clientService.saveClient(client);

        // Actualizar la sesión de autenticación si el correo electrónico o la contraseña se han cambiado
        Authentication newAuth = new UsernamePasswordAuthenticationToken(client.getEmail(), client.getPassword(), authentication.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(newAuth);

        return new ResponseEntity<>(new ClientDTO(client), HttpStatus.OK);
    }


}


