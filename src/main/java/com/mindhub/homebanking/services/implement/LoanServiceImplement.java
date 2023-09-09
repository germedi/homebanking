package com.mindhub.homebanking.services.implement;

import com.mindhub.homebanking.models.ClientLoan;
import com.mindhub.homebanking.models.Loan;
import com.mindhub.homebanking.repositories.*;
import com.mindhub.homebanking.services.LoanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class LoanServiceImplement  implements LoanService {
    @Autowired
    private LoanRepository loanRepository;
    @Autowired
    private TransactionRepository transactionRepository;
    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private ClientRepository clientRepository;
    @Autowired
    private ClientLoanRepository clientLoanRepository;

    @Override
    public List<Loan> getLoan() {
        return loanRepository.findAll();
    }

    @Override
    public void saveClientLoan(ClientLoan clientLoan) {

            clientLoanRepository.save(clientLoan);
        }


    @Override
    public Optional<Loan> findById(Long loanId) {
        return loanRepository.findById(loanId);
    }
}
