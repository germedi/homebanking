package com.mindhub.homebanking.controllers;

import com.mindhub.homebanking.dtos.AccountDTO;
import com.mindhub.homebanking.models.Account;
import com.mindhub.homebanking.repositories.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class AccountController {
    @Autowired  // Inyecta un objeto AccountRepository
    AccountRepository accountRepository;

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
}