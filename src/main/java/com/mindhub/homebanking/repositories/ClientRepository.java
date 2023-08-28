package com.mindhub.homebanking.repositories;

import com.mindhub.homebanking.models.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.Optional;

@RepositoryRestResource // Permite exponer los endpoints REST de esta interfaz
public interface ClientRepository extends JpaRepository<Client, Long>{ // Interfaz que extiende de JpaRepository para manejar la entidad Client y su identificador único de tipo Long
    Client findByEmail(String email);

    Optional<Client> findByEmailIgnoreCase(String nome);
}
/* Este código define la interfaz 'ClientRepository', que se utiliza para acceder a los datos de los clientes en la base de datos. */
