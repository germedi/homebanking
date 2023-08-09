package com.mindhub.homebanking.models;


import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    private long id; // Identificador único de la cuenta

    private String number; // Número de la cuenta
    private LocalDateTime creationDate ; // Fecha de creación de la cuenta
    private double balance; // Saldo de la cuenta

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="client_id")
    private Client client; // Cliente asociado a la cuenta

    @OneToMany(mappedBy="account", fetch= FetchType.EAGER)
    private Set<Transactions> transactions = new HashSet<Transactions>(); // Lista de transacciones asociadas a la cuenta

    // Constructores

    public Account() {
    }

    public Account(String number, LocalDateTime creationDate, double balance) {
        this.number = number;// nombre de la cuenta
        this.creationDate = creationDate; //fecha de creaccion de la cuenta
        this.balance = balance; //saldo de la cuenta
    }

    // Métodos getter y setter

    public void setId(long id) {
        this.id = id;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDateTime creationDate) {
        this.creationDate = creationDate;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public long getId() {
        return id;
    }

    public Set<Transactions> getTransaction() {
        return transactions;
    }

    public void setTransaction(Set<Transactions> transaction) {
        this.transactions = transaction;
    }

    // Método para agregar una transacción a la lista de transacciones de la cuenta

    public void addTransaction( Transactions transaction){
        transaction.setAccount(this);
        transactions.add(transaction);
    }
}
