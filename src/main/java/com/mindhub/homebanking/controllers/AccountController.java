package com.mindhub.homebanking.controllers;

import com.mindhub.homebanking.dtos.AccountDTO;
import com.mindhub.homebanking.models.Account;
import com.mindhub.homebanking.models.Client;
import com.mindhub.homebanking.repositories.AccountRepository;
import com.mindhub.homebanking.repositories.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class AccountController {

    @Autowired  // Inyecta un objeto AccountRepository
    AccountRepository accountRepository;

    @Autowired
    private ClientRepository clientRepository;

    // Mapea la URL "/api/accounts" a este método y devuelve una lista de objetos AccountDTO
    @GetMapping("/accounts")
    public List<AccountDTO> getAccounts()
    {
        List<Account> allAccounts = (List<Account>) accountRepository;

        // funcion map() recorre
        List<AccountDTO> converToListDto = allAccounts
                .stream()
                .map(account -> new AccountDTO(account))
                .collect(Collectors.toList());
        return converToListDto;
    }

    // Mapea la URL "/api/accounts/{id}" a este método y devuelve un objeto AccountDTO
    @GetMapping("/accounts/{id}")
    public AccountDTO getTransactionById(@PathVariable Long id)
    {
        Optional<Account> accountOptional = accountRepository.findById(id);
        return new AccountDTO(accountOptional.get());
    }
    @PostMapping("/clients/current/accounts")
    @PreAuthorize("hasAuthority('CLIENT')")
    public ResponseEntity<?> createAccount(Authentication authentication) {

        // Obtener información del cliente autenticado
        Client current = clientRepository.findByEmail(authentication.getName());

        // Verificar si el cliente ya tiene 3 cuentas registradas
        if (current.getAccounts().size() >= 3) {
            return new ResponseEntity<>("403 prohibido", HttpStatus.FORBIDDEN);
        }

        // Generar número aleatorio de 4 dígitos y crear número de cuenta
        Random rand = new Random();
        int random = rand.nextInt(9000) + 1000;
        String number = "VIN" + random;

        // Verificar si el número de cuenta ya existe en la base de datos
        while (accountRepository.findByNumber(number) != null && !accountRepository.findByNumber(number).getNumber().equals(number)) {
            random = rand.nextInt(9000) + 1000;
            number = "VIN" + random;
        }

        // Crear objeto Account
        Account account = new Account(number, LocalDateTime.now(), 0);

        // Asignar cuenta al cliente
        current.addAccount(account);

        // Guardar cuenta en la base de datos
        accountRepository.save(account);

        // Retornar respuesta "201 creada"
        return new ResponseEntity<>("201 creada", HttpStatus.CREATED);
    }

    @GetMapping ("/clients/current/accounts")
    public Set<AccountDTO> getAccountsCurrent(Authentication authentication) {
        return new HashSet<>(clientRepository.findByEmail(authentication.getName()).getAccounts()
                .stream()
                .map(AccountDTO::new)
                .collect(Collectors.toList()));
    }
}