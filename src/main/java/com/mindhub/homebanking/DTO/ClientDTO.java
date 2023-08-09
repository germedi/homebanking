package com.mindhub.homebanking.DTO;

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
    private String password;
    private Set<AccountDTO> accounts = new HashSet<>();

    // Constructor que crea un objeto ClientDTO a partir de un objeto Client

    public ClientDTO(Client client) {
        // Asigna los valores de las propiedades del objeto Client a las propiedades correspondientes del objeto ClientDTO
        this.id = client.getId(); // Asigna el id del cliente
        this.firstName = client.getFirstName(); // Asigna el nombre del cliente
        this.lastName = client.getLastName(); // Asigna el apellido del cliente
        this.email = client.getEmail(); // Asigna el correo electrónico del cliente
        this.password = client.getPassword(); // Asigna la contraseña del cliente
        // Convierte la lista de objetos Account del objeto Client a una lista de objetos AccountDTO utilizando la clase AccountDTO
        this.accounts = client.getAccounts() // Obtiene la lista de objetos Account del objeto Client
                .stream() // Convierte la lista en un Stream de objetos Account
                .map(accounts -> new AccountDTO(accounts)) // Convierte cada objeto Account en un objeto AccountDTO utilizando el constructor de AccountDTO
                .collect(Collectors.toSet()); // Convierte el Stream de objetos AccountDTO en un conjunto (Set) de objetos AccountDTO y lo asigna a la propiedad 'accounts' del objeto ClientDTO
    }

    // Métodos getter para acceder a las propiedades del objeto ClientDTO

    public long getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public Set<AccountDTO> getAccounts() {
        return accounts;
    }
}
