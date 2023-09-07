/* La clase Loan representa un tipo de préstamo en la base de datos.
Tiene campos para el nombre del préstamo, el monto máximo que se puede prestar, y
 una lista de cuotas para el préstamo. Además, tiene una relación "uno a muchos" con la
 entidad ClientLoan, que representa los préstamos asociados a este tipo de préstamo.
La clase también tiene métodos para acceder y modificar los valores de los campos.   */
package com.mindhub.homebanking.models;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.*;


/* La clase Loan representa un tipo de préstamo en la base de datos.
Tiene campos para el nombre del préstamo, el monto máximo que se puede prestar, y
 una lista de cuotas para el préstamo. Además, tiene una relación "uno a muchos" con la
 entidad ClientLoan, que representa los préstamos asociados a este tipo de préstamo.
La clase también tiene métodos para acceder y modificar los valores de los campos.   */

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static java.util.stream.Collectors.toList;


@Entity
public class Loan {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    private Long id;
    private String name;
    private Double maxAmount;

    @ElementCollection
    @Column (name="payments")
    private List<Integer> payments = new ArrayList<>();

    @OneToMany(mappedBy = "loan", fetch =FetchType.EAGER  )
    private Set<ClientLoan> clientLoans = new HashSet<>();

    public List<Client> getClients() {
        return clientLoans.stream().map(clientLoan -> clientLoan.getClient()).collect(toList());
    }

    public Loan() {
    }

    public Loan(String name, Double maxAmount, List<Integer> payments) {
        this.name = name;
        this.maxAmount = maxAmount;
        this.payments = payments;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getMaxAmount() {
        return maxAmount;
    }

    public void setMaxAmount(Double maxAmount) {
        this.maxAmount = maxAmount;
    }

    public List<Integer> getPayments() {
        return payments;
    }

    public void setPayments(List<Integer> payments) {
        this.payments = payments;
    }

    public void setClientLoans(Set<ClientLoan> clientLoans) {
        this.clientLoans = clientLoans;
    }

    public Set<ClientLoan> getLoan() {
        return clientLoans;
    }

}