package com.mindhub.homebanking;

import com.mindhub.homebanking.DTO.ClientDTO;
import com.mindhub.homebanking.models.Account;
import com.mindhub.homebanking.models.Client;
import com.mindhub.homebanking.repositories.AccountRepository;
import com.mindhub.homebanking.repositories.ClientRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.time.LocalDateTime;

@SpringBootApplication
public class HomebankingApplication {

	public static void main(String[] args) {
		SpringApplication.run(HomebankingApplication.class, args);

	}

	@Bean
	public CommandLineRunner initData(ClientRepository clientRepository,
									  AccountRepository accountRepository
	) {
		return (args) -> {
			Client client1 = new Client("Melba", "Morel", "melba@mindhub.com", "123");
			clientRepository.save(client1);

			//manipular tiempo de cuenta
			LocalDateTime creationDate = LocalDateTime.now();
			LocalDateTime tomorrow = creationDate.plusDays(1);
			Account account1= new Account("VIN0001", creationDate, 5000);
			Account account2 = new Account("VIN0002", tomorrow, 7500);

			client1.addAccount(account1);
			client1.addAccount(account2);

			ClientDTO clientDTO=new ClientDTO(client1);

			accountRepository.save(account1);
			accountRepository.save(account2);

			/*
			Account account1 = new Account("VIN0001", creationDate, 5000, client1);
			Account account2 = new Account("VIN0002", tomorrow, 7500, client1);
			accountRepository.save(account1);
			accountRepository.save(account2);
			 */
		};

	}
}