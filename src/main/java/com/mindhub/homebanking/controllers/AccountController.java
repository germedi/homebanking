package com.mindhub.homebanking.controllers;

import com.mindhub.homebanking.dtos.AccountDTO;
import com.mindhub.homebanking.repositories.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class AccountController {
    @Autowired  // Inyecta un objeto AccountRepository
    AccountRepository accountRepository;

    // Mapea la URL "/api/accounts" a este método y devuelve una lista de objetos AccountDTO
    @RequestMapping("/accounts")
    public List<AccountDTO> getaccountDTO() {
        return accountRepository.findAll()
                .stream().map(account -> new AccountDTO(account))
                .collect(Collectors.toList());
    }

    // Mapea la URL "/api/accounts/{id}" a este método y devuelve un objeto AccountDTO
    @RequestMapping("/accounts/{id}")
    public AccountDTO accountDTO(@PathVariable Long id) {
        // Crea un objeto AccountDTO a partir del objeto Account con el id
        return new AccountDTO(accountRepository.findById(id).orElse(null));

    }
}