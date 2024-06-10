

/* Un DTO es un objeto que se utiliza para transportar datos entre capas de una aplicación o entre diferentes aplicaciones.
 En este caso, la clase 'ClientDTO' se utiliza para transportar datos entre la capa de presentación y
 la capa de acceso a datos de una aplicación bancaria.
La clase 'ClientDTO' tiene las mismas propiedades que la clase 'Client',
pero con la diferencia de que las propiedades de 'ClientDTO' son públicas y se pueden acceder desde cualquier
 parte de la aplicación. Además, la clase 'ClientDTO', tiene métodos getter para acceder a las propiedades.  */

package com.mindhub.homebanking.dtos;

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


    private Set<AccountDTO> accounts = new HashSet<>();
    private Set<ClientLoanDTO> loans = new HashSet<>();
    private Set<CardDTO> cards = new HashSet<>();

    public ClientDTO() {
        // Constructor predeterminado
    }

    public ClientDTO(Client client) {
        this.id = client.getId();
        this.firstName = client.getFirstName();
        this.lastName = client.getLastName();
        this.email = client.getEmail();
        this.password = client.getPassword();


        this.accounts = client.getAccounts().stream()
                .map(AccountDTO::new)
                .collect(Collectors.toSet());

        this.loans = client.getLoans().stream()
                .map(ClientLoanDTO::new)
                .collect(Collectors.toSet());

        this.cards = client.getCards().stream()
                .map(CardDTO::new)
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



    public Set<AccountDTO> getAccounts() {
        return accounts;
    }

    public void setAccounts(Set<AccountDTO> accounts) {
        this.accounts = accounts;
    }

    public Set<ClientLoanDTO> getLoans() {
        return loans;
    }

    public void setLoans(Set<ClientLoanDTO> loans) {
        this.loans = loans;
    }

    public Set<CardDTO> getCards() {
        return cards;
    }

    public void setCards(Set<CardDTO> cards) {
        this.cards = cards;
    }

    public String getPlainPassword() {
        return this.password;
    }
}
