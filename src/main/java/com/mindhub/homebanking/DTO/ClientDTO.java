package com.mindhub.homebanking.DTO;

import com.mindhub.homebanking.models.Client;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public class ClientDTO {
    private long id;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private Set<AccountDTO> accounts= new HashSet<>();

    public Set<AccountDTO> getAccounts() {
        return accounts;
    }

    public ClientDTO(Client client){
        this.id= client.getId();
        this.firstName= client.getFirstName();
        this.lastName= client.getLastName();
        this.email=client.getEmail();
        this.password= client.getPassword();
        this.accounts= client.getAccounts()
                      .stream()
                      .map(accounts -> new AccountDTO(accounts))
                      .collect(Collectors.toSet());

    }



    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
