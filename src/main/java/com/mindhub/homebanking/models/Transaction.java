package com.mindhub.homebanking.models;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity // Indica que la clase es una entidad JPA
public class Transaction {
    //Esta clase representa una transacción financiera y tiene los siguientes atributos

    @Id // Indica que el atributo id es la clave primaria de la tabla correspondiente
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native") // Indica que el valor de la clave primaria se generará automáticamente al insertar una nueva fila en la tabla
    @GenericGenerator(name = "native", strategy = "native") // Especifica el generador de claves primarias que se utilizará
    private Long id; // Identificador único de la transacción

    private TransactionType type; // Tipo de transacción (puede ser DEPOSIT o WITHDRAWAL)
    private double amount; // Cantidad de dinero involucrada en la transacción
    private String description; // Descripción de la transacción
    private LocalDateTime date; // Fecha en que se realizó la transacción

    @ManyToOne(fetch = FetchType.EAGER) // Especifica que hay una relación Many-to-One entre la entidad Transaction y la entidad Account
    @JoinColumn(name="accountId") // Especifica el nombre de la columna en la tabla de transacciones que contiene el ID de la cuenta bancaria correspondiente
    private Account account; // Cuenta bancaria a la que pertenece la transacción

    public Transaction(Account account, TransactionType credit, Double amount, String s, LocalDateTime now) {
        // Constructor por defecto sin argumentos
    }

    public Transaction() {
    }

    public Transaction(TransactionType type, double amount, String description, LocalDateTime date) // Constructor que recibe los valores de los atributos como parámetros y los asigna a los correspondientes atributos de la clase
    {
        this.type = type;
        this.amount = amount;
        this.description = description;
        this.date = date;
    }



    public Transaction(Double amount, String description, LocalDateTime now, Account originAccount, Account destinyAccount) {
    }

    public Transaction(LocalDateTime now, double amount, String s, TransactionType transactionType, Account account) {
    }

    public Long getId() { // Método getter para el atributo id
        return id;
    }

    public void setId(Long id) { // Método setter para el atributo id
        this.id = id;
    }

    public TransactionType getType() { // Método getter para el atributo type
        return type;
    }

    public void setType(TransactionType type) { // Método setter para el atributo type
        this.type = type;
    }

    public double getAmount() { // Método getter para el atributo amount
        return amount;
    }

    public void setAmount(double amount) { // Método setter para el atributo amount
        this.amount = amount;
    }

    public String getDescription() { // Método getter para el atributo description
        return description;
    }

    public void setDescription(String description) { // Método setter para el atributo description
        this.description = description;
    }

    public LocalDateTime getDate() { // Método getter para el atributo date
        return date;
    }

    public void setDate(LocalDateTime date) { // Método setter para el atributo date
        this.date = date;
    }

    public Account getAccount() { // Método getter para el atributo account
        return account;
    }

    public void setAccount(Account account) { // Método setter para el atributo account
        this.account = account;
    }
}
