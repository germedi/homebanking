package com.mindhub.homebanking.services;

import com.mindhub.homebanking.models.Loan;

import java.util.List;
import java.util.Optional;

public interface LoanService {
    public List<Loan> getLoan();


    public Optional<Loan> findById(Long loanId);
}