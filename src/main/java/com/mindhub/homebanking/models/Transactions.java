package com.mindhub.homebanking.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
public class Transactions {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native") // Identificador único de la transacción generado automáticamente
    @GenericGenerator(name = "native", strategy = "native")
    private long id;

    private TransactionsType type; // Tipo de transacción (DEBIT, CREDIT)
    private Double amount; // Cantidad de la transacción
    private String description; // Descripción de la transacción
    private LocalDateTime date; // Fecha y hora de la transacción

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "account_id")
    private Account account; // Cuenta asociada a la transacción

    // Constructores

    public Transactions() {
    }

    public Transactions(TransactionsType type, Double amount, String description, LocalDateTime date ,Account account) {
        this.type = type;
        this.amount = amount;
        this.description = description;
        this.date = date;
        this.account=account;
    }

    // Métodos getter y setter

    @JsonIgnore
    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public TransactionsType getType() {
        return type;
    }

    public void setType(TransactionsType type) {
        this.type = type;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public long getId() {
        return id;
    }
}
