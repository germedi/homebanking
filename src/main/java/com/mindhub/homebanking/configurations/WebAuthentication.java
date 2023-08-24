package com.mindhub.homebanking.configurations;

import com.mindhub.homebanking.models.Client;
import com.mindhub.homebanking.repositories.ClientRepository;
import org.springframework.security.core.userdetails.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.GlobalAuthenticationConfigurerAdapter;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
@Configuration // Indica que esta clase es una configuración
public class WebAuthentication extends GlobalAuthenticationConfigurerAdapter {
    // Inyecta una dependencia de ClientRepository
    @Autowired
    ClientRepository clientRepository;

    @Override
    // Inicializa el AuthenticationManagerBuilder
    public void init(AuthenticationManagerBuilder auth) throws Exception {
        // Define un servicio de detalles de usuario personalizado
        auth.userDetailsService(email-> {
            // Busca al cliente en la base de datos utilizando su correo electrónico
            Client client = clientRepository.findByEmail(email);
            if (client != null) { // Si el cliente existe en la base de datos, se procede con la autenticación
                if (client.isAdmin()) { // Si el cliente tiene el rol de "ADMIN", se le asigna el rol de "ADMIN"
                    // Crea un objeto User con el correo electrónico y la contraseña del cliente, y se le asigna el rol de "ADMIN"
                    return new User(client.getEmail(), client.getPassword(), AuthorityUtils.createAuthorityList("ADMIN"));
                } else { // Si el cliente no tiene el rol de "ADMIN", se le asigna el rol de "CLIENT"
                    // Crea un objeto User con el correo electrónico y la contraseña del cliente, y se le asigna el rol de "CLIENT"
                    return new User(client.getEmail(), client.getPassword(), AuthorityUtils.createAuthorityList("CLIENT"));
                }
            } else { // Si el correo electrónico del cliente no existe en la base de datos, se lanza una excepción indicando que el usuario es desconocido
                throw new UsernameNotFoundException("Unknown user: " + email);
            }
        });
    }

    // Define un Bean que encripta la contraseña utilizando PasswordEncoderFactories
    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }
}
