package com.mindhub.homebanking.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity // Anotación que indica que esta clase es una entidad de la base de datos
public class ClientLoan {
    @Id // Anotación que indica que este campo es la clave primaria de la entidad
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native") // Anotación que indica que el valor de este campo se genera automáticamente
    @GenericGenerator(name = "native", strategy = "native") // Anotación que indica el generador de valor para la clave primaria
    private long id; // Campo que representa la clave primaria de la entidad

    private double amount; // Campo que representa el monto del préstamo
    private int payments; // Campo que representa el número de pagos del préstamo

    @ManyToOne(fetch = FetchType.EAGER) // Anotación que indica que esta entidad tiene una relación "muchos a uno" con otra entidad
    @JoinColumn(name = "clientId") // Anotación que indica el nombre de la columna que representa la clave foránea en la tabla de esta entidad
    private Client client; // Campo que representa el cliente asociado a este préstamo

    @ManyToOne(fetch = FetchType.EAGER) // Anotación que indica que esta entidad tiene una relación "muchos a uno" con otra entidad
    @JoinColumn(name = "loanId") // Anotación que indica el nombre de la columna que representa la clave foránea en la tabla de esta entidad
    private Loan loan; // Campo que representa el préstamo asociado a este cliente


    public ClientLoan() {
    }

    public ClientLoan(double amount, int payments, Client client, Loan loan) {
        this.amount = amount;
        this.payments = payments;
        this.client = client;
        this.loan = loan;
    }

    public ClientLoan(LocalDateTime now, double amount, Loan loan, Client client) {
    }



    public long getId() {
        return id;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public int getPayments() {
        return payments;
    }

    public void setPayments(int payments) {
        this.payments = payments;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public Loan getLoan() {
        return loan;
    }

    public void setLoan(Loan loan) {
        this.loan = loan;
    }


}
/* La clase ClientLoan representa un préstamo asociado a un cliente en la base de datos.
lo entendi como una tabla intermedia entre client y loan
Tiene campos para el monto del préstamo, el número de pagos, y una relación "muchos a uno" con
la entidad Client y la entidad Loan.
Además, tiene métodos para acceder y modificar los valores de los campos.*/