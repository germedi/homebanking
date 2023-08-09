package com.mindhub.homebanking;

import com.mindhub.homebanking.DTO.ClientDTO;
import com.mindhub.homebanking.models.Account;
import com.mindhub.homebanking.models.Client;
import com.mindhub.homebanking.models.Transactions;
import com.mindhub.homebanking.repositories.AccountRepository;
import com.mindhub.homebanking.repositories.ClientRepository;
import com.mindhub.homebanking.repositories.TransactionRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.time.LocalDateTime;

import static com.mindhub.homebanking.models.TransactionsType.CREDIT;
import static com.mindhub.homebanking.models.TransactionsType.DEBIT;

@SpringBootApplication
public class HomebankingApplication {

	public static void main(String[] args) {
		SpringApplication.run(HomebankingApplication.class, args);

	}

	@Bean
	public CommandLineRunner initData(ClientRepository clientRepository,
									  AccountRepository accountRepository, TransactionRepository transactionRepository
	) {
		// Retorna un objeto CommandLineRunner que se ejecutará al inicio de la aplicación
		return (args) -> {
			// Crea un objeto Client y lo guarda en el repositorio
			Client client1 = new Client("Melba", "Morel", "melba@mindhub.com", "123");
			clientRepository.save(client1);

			//manipular tiempo de cuenta
			LocalDateTime creationDate = LocalDateTime.now();
			LocalDateTime tomorrow = creationDate.plusDays(1);
			// Crea dos objetos Account y los agrega al cliente
			Account account1= new Account("VIN0001", creationDate, 5000);
			Account account2 = new Account("VIN0002", tomorrow, 7500);

			client1.addAccount(account1);
			client1.addAccount(account2);

			// Crea un objeto ClientDTO a partir del objeto Client
			ClientDTO clientDTO=new ClientDTO(client1);

			// Guarda los objetos Account creados anteriormente en el repositorio accountRepository
			accountRepository.save(account1);
			accountRepository.save(account2);

			Transactions transactions1= new Transactions(DEBIT,200.0,"compra", LocalDateTime.now(),account1);
			Transactions transactions2= new Transactions(CREDIT,220.0,"venta",tomorrow,account2);
			transactionRepository.save(transactions1);
			transactionRepository.save(transactions2);
			Transactions transactions3= new Transactions(DEBIT,300.0,"compra",LocalDateTime.now(),account2);
			Transactions transactions4= new Transactions(CREDIT,500.0,"venta",LocalDateTime.now(),account1 );
			transactionRepository.save(transactions3);
			transactionRepository.save(transactions4);

			/*
			Account account1 = new Account("VIN0001", creationDate, 5000, client1);
			Account account2 = new Account("VIN0002", tomorrow, 7500, client1);
			accountRepository.save(account1);
			accountRepository.save(account2);
			 */
		};

	}
}