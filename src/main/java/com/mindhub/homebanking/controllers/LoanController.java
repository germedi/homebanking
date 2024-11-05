package com.mindhub.homebanking.controllers;

import com.mindhub.homebanking.dtos.LoanApplicationDTO;
import com.mindhub.homebanking.dtos.LoanDTO;
import com.mindhub.homebanking.models.*;
import com.mindhub.homebanking.services.AccountService;
import com.mindhub.homebanking.services.ClientService;
import com.mindhub.homebanking.services.LoanService;
import com.mindhub.homebanking.services.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;
import static com.mindhub.homebanking.models.TransactionType.CREDIT;

@RestController
@RequestMapping("/api")
public class LoanController {

    @Autowired
    LoanService loanService;
    @Autowired
    ClientService clientService;
    @Autowired
    AccountService accountService;
    @Autowired
    TransactionService transactionService;

    @GetMapping("/loans")
    public List<LoanDTO> getAll() {
        return loanService.getLoan().stream().map(LoanDTO::new).collect(Collectors.toList());
    }

    @PostMapping("/loans")
    public ResponseEntity<Object> createLoans(@RequestBody LoanApplicationDTO loanApplicationDTO, Authentication authentication) {
        try {
            Client client = clientService.getClientByEmail(authentication.getName());
            Optional<Loan> loan = loanService.findById(loanApplicationDTO.getLoanId());
            Optional<Account> account = accountService.findByNumber(loanApplicationDTO.getToAccountNumber());
            Account creditAccount = accountService.getAccountByNumber(loanApplicationDTO.getToAccountNumber());

            // Verificar que el préstamo exista
            if (loan.isEmpty()) {
                return new ResponseEntity<>("Loan doesn't exist", HttpStatus.FORBIDDEN);
            }

            // Verificar que la cuenta exista
            if (account.isEmpty()) {
                return new ResponseEntity<>("Account doesn't exist", HttpStatus.FORBIDDEN);
            }

            // Verificar que los datos sean correctos
            if (loanApplicationDTO.getLoanId() == 0 || loanApplicationDTO.getToAccountNumber().isEmpty() ||
                    loanApplicationDTO.getAmount() == 0 || loanApplicationDTO.getPayments() == 0) {
                return new ResponseEntity<>("Empty parameters", HttpStatus.FORBIDDEN);
            }

            // Verificar que el monto solicitado no exceda el monto máximo del préstamo
            if (loanApplicationDTO.getAmount() > loan.get().getMaxAmount()) {
                return new ResponseEntity<>("Exceeds max amount", HttpStatus.FORBIDDEN);
            }

            // Verificar que el monto del préstamo sea mayor a 49999
            if (loanApplicationDTO.getAmount() < 49999) {
                return new ResponseEntity<>("The loan must be greater than 49999", HttpStatus.FORBIDDEN);
            }

            // Verificar que la cantidad de cuotas se encuentre entre las disponibles del préstamo
            if (!loan.get().getPayments().contains(loanApplicationDTO.getPayments())) {
                return new ResponseEntity<>("Error", HttpStatus.EXPECTATION_FAILED);
            }

            // Verificar que la cuenta de destino pertenezca al cliente autenticado
            if (!authentication.getName().equals(account.get().getClient().getEmail())) {
                return new ResponseEntity<>("Not authorized", HttpStatus.FORBIDDEN);
            }

            // Calcular el monto del préstamo con interés
            Double loanInterest = ((loanApplicationDTO.getAmount() * 0.20) + loanApplicationDTO.getAmount());

            // Crear transacción y actualizar el saldo de la cuenta
            Transaction transaction = new Transaction(CREDIT, loanApplicationDTO.getAmount(), "description" + " " + "loan approved", LocalDateTime.now());
            transactionService.save(transaction);
            creditAccount.addTransaction(transaction);
            Double balanceToAccount = creditAccount.getBalance();
            creditAccount.setBalance(balanceToAccount + loanApplicationDTO.getAmount());

            // Guardar la solicitud de préstamo
            ClientLoan clientLoan = new ClientLoan(loanInterest, loanApplicationDTO.getPayments(), client, loan.get());
            loanService.saveClientLoan(clientLoan);

            // Guardar la cuenta después de actualizar el saldo
            accountService.save(creditAccount);

            return new ResponseEntity<>("Loan approved", HttpStatus.CREATED);

        } catch (Exception ex) {
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return new ResponseEntity<>("Unexpected", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


}
