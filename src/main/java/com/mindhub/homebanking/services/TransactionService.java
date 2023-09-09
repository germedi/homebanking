package com.mindhub.homebanking.services;

import com.mindhub.homebanking.dtos.TransactionDTO;
import com.mindhub.homebanking.models.Client;
import com.mindhub.homebanking.models.Transaction;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

public interface TransactionService {


    List<TransactionDTO> getClientAutenticado(Authentication authentication);

    public void save(Transaction transaction);

    List<TransactionDTO> getTransaction(long id);

}
