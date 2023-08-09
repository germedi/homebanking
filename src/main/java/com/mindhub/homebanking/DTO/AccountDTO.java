package com.mindhub.homebanking.DTO;

import com.mindhub.homebanking.models.Account;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

public class AccountDTO {
    private long id;
    private String number;
    private LocalDateTime creationDate;
    private double balance;
    private List<TransactionDTO> transactions;

    public AccountDTO() {
    }

    // Constructor que crea un objeto AccountDTO a partir de un objeto Account
    public AccountDTO(Account account) {
        // Asigna los valores de las propiedades del objeto Account a las propiedades correspondientes del objeto AccountDTO
        this.id = account.getId(); // Asigna el id de la cuenta
        this.number = account.getNumber(); // Asigna el número de la cuenta
        this.creationDate = account.getCreationDate(); // Asigna la fecha de creación de la cuenta
        this.balance = account.getBalance(); // Asigna el saldo de la cuenta
        // Convierte la lista de objetos Transaction a una lista de objetos TransactionDTO utilizando la clase TransactionDTO
        this.transactions = account.getTransaction() // Obtiene la lista de objetos Transaction del objeto Account
                .stream() // Convierte la lista en un Stream de objetos Transaction
                .map(transaction -> new TransactionDTO(transaction)) // Convierte cada objeto Transaction en un objeto TransactionDTO utilizando el constructor de TransactionDTO
                .collect(Collectors.toList()); // Convierte el Stream de objetos TransactionDTO en una lista de objetos TransactionDTO y lo asigna a la propiedad 'transactions' del objeto AccountDTO
    }

    // Métodos getter para acceder a las propiedades del objeto AccountDTO

    public long getId() {  return id; }

    public String getNumber() { return number;  }

    public LocalDateTime getDate() { return creationDate; }

    public double getBalance() { return balance; }

    public List<TransactionDTO> getTransactions() { return transactions;  } //devuelve la lista de transacciones de la cuenta bancaria en forma de objetos 'TransactionDTO'
}
