/* La clase Loan representa un tipo de préstamo en la base de datos.
Tiene campos para el nombre del préstamo, el monto máximo que se puede prestar, y
 una lista de cuotas para el préstamo. Además, tiene una relación "uno a muchos" con la
 entidad ClientLoan, que representa los préstamos asociados a este tipo de préstamo.
La clase también tiene métodos para acceder y modificar los valores de los campos.   */
package com.mindhub.homebanking.models;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


@Entity
public class Loan {
        @Id
        @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
        @GenericGenerator(name = "native", strategy = "native")
        private Long id;
        private String name;
        private double maxAmount;
        @ElementCollection
        @Column(name = "payments")// +o payment_id???
        private List<Integer> payments = new ArrayList<>(); // payments = cuotas

        @OneToMany(mappedBy = "loan", fetch = FetchType.EAGER)
        private Set<ClientLoan> clients = new HashSet<>();

        public Loan() {
        }

        public Loan(String name, double maxAmount, List<Integer> payments) {
            this.name = name;
            this.maxAmount = maxAmount;
            this.payments = payments;
        }

        public Long getId() {
            return id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public double getMaxAmount() {
            return maxAmount;
        }

        public void setMaxAmount(double maxAmount) {
            this.maxAmount = maxAmount;
        }

        public List<Integer> getPayments() {
            return payments;
        }

        public void setPayments(List<Integer> payments) {
            this.payments = payments;
        }

        public Set<ClientLoan> getClients() {
            return clients;
        }

        public void setClients(Set<ClientLoan> clients) {
            this.clients = clients;
        }
    }
