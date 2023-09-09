package com.mindhub.homebanking.services.implement;

import com.mindhub.homebanking.dtos.TransactionDTO;
import com.mindhub.homebanking.models.Client;
import com.mindhub.homebanking.models.Transaction;
import com.mindhub.homebanking.repositories.AccountRepository;
import com.mindhub.homebanking.repositories.ClientRepository;
import com.mindhub.homebanking.repositories.TransactionRepository;
import com.mindhub.homebanking.services.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TransactionServiceImplement implements TransactionService {
    // Inyectar el repositorio de cuentas
    @Autowired
    private AccountRepository accountRepository;

    // Inyectar el repositorio de transacciones
    @Autowired
    private TransactionRepository transactionRepository;

    // Inyectar el repositorio de clientes
    @Autowired
    private ClientRepository clientRepository;





    @Override
    public List<TransactionDTO> getClientAutenticado(Authentication authentication) {
        final Client byEmail = clientRepository.findByEmail(authentication.getName());
        return (List<TransactionDTO>) byEmail;
    }

    @Override
    public void save(Transaction transaction) {
        transactionRepository.save(transaction);
    }

    @Override
    public List<TransactionDTO> getTransaction(long id) {
        return (List<TransactionDTO>) new TransactionDTO(transactionRepository.findById(id).orElse(null));
    }
}
