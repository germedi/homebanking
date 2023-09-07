package com.mindhub.homebanking.services;

import com.mindhub.homebanking.dtos.AccountDTO;
import com.mindhub.homebanking.models.Account;
import com.mindhub.homebanking.models.Client;
import org.springframework.security.core.Authentication;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface AccountService {
    public List<AccountDTO> getAccounts();
    public AccountDTO getTransactionById(long id);
    public Client clientAuthentication(Authentication authentication);

    public void save(Account account);

    public <string> Optional<Account> findByNumber(String number);



    public Set<AccountDTO> getAccountsCurrent(Authentication authentication);
}
