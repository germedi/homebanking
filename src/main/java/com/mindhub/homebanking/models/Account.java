package com.mindhub.homebanking.models;
import org.hibernate.annotations.GenericGenerator;
import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity // Anotación que indica que esta clase es una entidad de la base de datos
public class Account {
    @Id // Anotación que indica que este campo es la clave primaria de la entidad
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native") // Anotación que indica que el valor de este campo se genera automáticamente
    @GenericGenerator(name = "native", strategy = "native") // Anotación que indica el generador de valor para la clave primaria
    private long id; // Campo que representa la clave primaria de la entidad

    private String number; // Campo que representa el número de la cuenta
    private LocalDateTime creationDate; // Campo que representa la fecha de creación de la cuenta
    private double balance; // Campo que representa el saldo de la cuenta



    @ManyToOne(fetch = FetchType.EAGER) // Anotación que indica que esta entidad tiene una relación "muchos a uno" con otra entidad
    @JoinColumn(name="clientId") // Anotación que indica el nombre de la columna que representa la clave foránea en la tabla de esta entidad
    private Client client; // Campo que representa el cliente asociado a esta cuenta

    @OneToMany(mappedBy="account", fetch=FetchType.EAGER) // Anotación que indica que esta entidad tiene una relación "uno a muchos" con otra entidad
    private Set<Transaction> transactions = new HashSet<>(); // Campo que representa las transacciones asociadas a esta cuenta
    //constructor vacio
    public Account() {
    }
    //constructor de cada atributo
    public Account(String number, LocalDateTime date, double balance) {
        this.number = number;
        this.creationDate = date;
        this.balance = balance;
    }
    //getter y setter
    public long getId()
    {
        return id;
    }

    public String getNumber()
    {
        return number;
    }

    public void setNumber(String number)
    {
        this.number = number;
    }

    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDateTime creationDate) {
        this.creationDate = creationDate;
    }

    public double getBalance()
    {
        return balance;
    }

    public void setBalance(double balance)
    {
        this.balance = balance;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public Set<Transaction> getTransactions()
    {
        return transactions;
    }

    public void addTransaction(Transaction transaction)
    {
        transaction.setAccount(this);
        transactions.add(transaction);
    }
}
