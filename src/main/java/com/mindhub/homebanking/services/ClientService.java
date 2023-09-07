package com.mindhub.homebanking.services;

import com.mindhub.homebanking.dtos.CardDTO;
import com.mindhub.homebanking.dtos.ClientDTO;
import com.mindhub.homebanking.models.Client;
import org.springframework.security.core.Authentication;

import javax.persistence.Tuple;
import java.util.List;


public interface ClientService {


    public List<ClientDTO> getClients();
    public ClientDTO getClientById(long id);

    public ClientDTO getClientDto(Authentication authentication);

    public Client getClientByEmail(String email);

    public void saveClient(Client client);



   public Tuple findByEmailIgnoreCase(String name, Authentication authentication);
}
