package com.mindhub.homebanking.repositories;

import com.mindhub.homebanking.models.Transactions;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public  interface TransactionRepository extends JpaRepository<Transactions,Long> {

}
