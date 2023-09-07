package com.mindhub.homebanking.services.implement;


import com.mindhub.homebanking.dtos.AccountDTO;
import com.mindhub.homebanking.models.Account;
import com.mindhub.homebanking.models.Client;
import com.mindhub.homebanking.repositories.AccountRepository;
import com.mindhub.homebanking.repositories.ClientRepository;
import com.mindhub.homebanking.services.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class AccountServiceImplement implements AccountService {
    @Autowired  // Inyecta un objeto AccountRepository
    AccountRepository accountRepository;
    @Autowired
    private ClientRepository clientRepository;
    @Override
    public List<AccountDTO> getAccounts() {
        List<Account> allAccounts = (List<Account>) accountRepository;

        // funcion map() recorre
        List<AccountDTO> converToListDto = allAccounts
                .stream()
                .map(account -> new AccountDTO(account))
                .collect(Collectors.toList());
        return converToListDto;
    }

    @Override
    public AccountDTO getTransactionById(long id) {
        Optional<Account> accountOptional = accountRepository.findById(id);
        return new AccountDTO(accountOptional.get());
    }

    @Override
    public Client clientAuthentication(Authentication authentication) {
        return clientRepository.findByEmail(authentication.getName());
    }


    @Override
    public void save(Account account) {
        accountRepository.save(account);
    }

    @Override
    public <string> Optional<Account> findByNumber(String number) {
        return Optional.ofNullable(accountRepository.findByNumber(number));
    }


    @Override
    public Set<AccountDTO> getAccountsCurrent(Authentication authentication) {
        return new HashSet<>(clientRepository.findByEmail(authentication.getName()).getAccounts()
                .stream()
                .map(AccountDTO::new)
                .collect(Collectors.toList()));
    }
}
