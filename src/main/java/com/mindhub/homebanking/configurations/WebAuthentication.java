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
            if (client != null) {
                if (client.getEmail().equalsIgnoreCase("gerardomedinav@gmail.com")) {
                    return new User(client.getEmail(), client.getPassword()
                            , AuthorityUtils.createAuthorityList("ADMIN"));
                } else {
                    return new User(client.getEmail(), client.getPassword()
                            , AuthorityUtils.createAuthorityList("CLIENT"));
                }
            } else {
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
