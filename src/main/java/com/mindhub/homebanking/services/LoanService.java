package com.mindhub.homebanking.services;

import com.mindhub.homebanking.models.ClientLoan;
import com.mindhub.homebanking.models.Loan;

import java.util.List;
import java.util.Optional;

public interface LoanService {
    public List<Loan> getLoan();

    public void saveClientLoan(ClientLoan clientLoan);


    public Optional<Loan> findById(Long loanId);

}
