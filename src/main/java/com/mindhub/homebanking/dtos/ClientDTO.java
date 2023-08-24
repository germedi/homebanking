package com.mindhub.homebanking.dtos;

import com.mindhub.homebanking.models.Client;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

/* Un DTO es un objeto que se utiliza para transportar datos entre capas de una aplicación o entre diferentes aplicaciones.
 En este caso, la clase 'ClientDTO' se utiliza para transportar datos entre la capa de presentación y
 la capa de acceso a datos de una aplicación bancaria.
La clase 'ClientDTO' tiene las mismas propiedades que la clase 'Client',
pero con la diferencia de que las propiedades de 'ClientDTO' son públicas y se pueden acceder desde cualquier
 parte de la aplicación. Además, la clase 'ClientDTO', tiene métodos getter para acceder a las propiedades.  */

public class ClientDTO {
    private long id;
    private String firstName;
    private String lastName;
    private String email;
    private  Boolean admin;

    private Set<AccountDTO> accounts = new HashSet<>();

    private Set<ClientLoanDTO> loans = new HashSet<>();

    private Set<CardDTO> cards= new HashSet<>();

    public ClientDTO(Client client)
    {
        this.id = client.getId();
        this.firstName = client.getFirstName();
        this.lastName = client.getLastName();
        this.email = client.getEmail();


        this.accounts = client
                .getAccounts()
                .stream()
                .map(account -> new AccountDTO(account))
                .collect(Collectors.toSet());

        this.loans = client
                .getLoans()
                .stream()
                .map(loan -> new ClientLoanDTO(loan))
                .collect(Collectors.toSet());
        this.cards = client
                .getCards()
                .stream()
                .map(card -> new CardDTO(card))
                .collect(Collectors.toSet());

    }

    public long getId()
    {
        return id;
    }

    public String getFirstName()
    {
        return firstName;
    }

    public String getLastName()
    {
        return lastName;
    }

    public String getEmail()
    {
        return email;
    }



    public Set<AccountDTO> getAccounts()
    {
        return accounts;
    }

    public Set<ClientLoanDTO> getLoans() {
        return loans;
    }
    public Set<CardDTO> getCards() { return cards;  }
}