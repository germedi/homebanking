package com.mindhub.homebanking.DTO;

import com.mindhub.homebanking.models.Transactions;
import com.mindhub.homebanking.models.TransactionsType;

import javax.transaction.Transaction;
import java.time.LocalDateTime;


public class TransactionDTO {
    private long id;
    private TransactionsType type;
    private Double amount;
    private String description;
    private LocalDateTime date;

    public TransactionDTO() {
    }

    public TransactionDTO(Transactions transactions) {
        this.id = transactions.getId();
        this.amount = transactions.getAmount();
        this.description = transactions.getDescription();
        this.date =transactions.getDate();
        this.type=transactions.getType();
    }


    public TransactionsType getType() {
        return type;
    }

    public long getId() {
        return id;
    }

    public Double getAmount() {
        return amount;
    }


    public String getDescription() {
        return description;
    }


    public LocalDateTime getDate() {
        return date;
    }


}