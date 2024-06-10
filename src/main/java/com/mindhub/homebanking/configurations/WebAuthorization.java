package com.mindhub.homebanking.configurations;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.WebAttributes;
import org.springframework.security.web.authentication.logout.HttpStatusReturningLogoutSuccessHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@EnableWebSecurity // Habilita la seguridad web
@Configuration // Indica que esta clase es una configuración
class WebAuthorization extends WebSecurityConfigurerAdapter {
    // Configura la autorización de solicitudes HTTP
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // Si quiero acceder con ("/web/index.html", "SIN ROL") puedo hacerlo, pero
        // si cambio el orden y pongo esta regla primero '.antMatchers("/**").hasAuthority("CLIENT")'
        // me va a rechazar la autorización, por que analiza primero esa linea.
        /*
        http.authorizeRequests()
                .antMatchers("/web/index.html","/web/css/**","/web/img/**","/web/js/**","/api/loans").permitAll()//
                .antMatchers(HttpMethod.POST, "/api/login","/api/logout","/api/clients").permitAll()
                .antMatchers(HttpMethod.POST,"/api/clients/current/accounts","/api/clients/current/cards"
                        ,"/api/transactions","/api/loans").hasAnyAuthority("CLIENT","ADMIN")
                .antMatchers("/web/**.html").hasAnyAuthority("CLIENT","ADMIN")

                .antMatchers("/api/**").hasAnyAuthority("CLIENT","ADMIN")
                //.antMatchers("/api/clients","/api/accounts","/api/cards","api/transactions"
                //       ,"api/clients/{id}","/api/accounts/{id}","/api/transaction/{id}",
                //       "/api/clients/current","/api/clients/current/accounts","/api/clients/current/cards").hasAnyAuthority("CLIENT","ADMIN")


                .anyRequest().denyAll();
                */

        http.authorizeRequests()
                .antMatchers("/web/index.html", "/web/css/**", "/web/img/**", "/web/js/**").permitAll()
                .antMatchers(HttpMethod.POST, "/api/login").permitAll()
                .antMatchers(HttpMethod.POST, "/api/clients").permitAll()
                .antMatchers(HttpMethod.POST, "/api/clients/current/accounts").hasAnyAuthority("CLIENT","ADMIN")
                .antMatchers(HttpMethod.POST, "/api/clients/current/cards").hasAnyAuthority("CLIENT","ADMIN")
                .antMatchers(HttpMethod.GET, "/api/clients/current").hasAnyAuthority("CLIENT","ADMIN")
                .antMatchers(HttpMethod.PUT, "/api/clients/current").hasAnyAuthority("CLIENT","ADMIN")
                .antMatchers(HttpMethod.POST, "/api/transfers").hasAnyAuthority("CLIENT","ADMIN")
                .antMatchers(HttpMethod.GET, "/api/clients/current/accounts").hasAnyAuthority("CLIENT","ADMIN")
                .antMatchers(HttpMethod.GET, "/api/Transfers").hasAnyAuthority("CLIENT","ADMIN")
                .antMatchers(HttpMethod.GET, "/api/accounts/{id}").hasAnyAuthority("CLIENT","ADMIN")///accounts/{id}
                .antMatchers(HttpMethod.POST, "/api/transactions/**").hasAnyAuthority("CLIENT","ADMIN")
                .antMatchers(HttpMethod.GET, "/api/transaction/{id}").hasAnyAuthority("CLIENT","ADMIN")
                .antMatchers(HttpMethod.GET, "/clients/{id}/loans").hasAnyAuthority("CLIENT","ADMIN")
                .antMatchers(HttpMethod.GET, "/api/loans").hasAnyAuthority("CLIENT","ADMIN")
                .antMatchers(HttpMethod.POST, "/api/loans").hasAnyAuthority("CLIENT","ADMIN")
                .antMatchers("/web/accounts.html").hasAnyAuthority("CLIENT","ADMIN")//
                .antMatchers(HttpMethod.PATCH,"/api/clients/current/cards/delete").hasAnyAuthority("CLIENT","ADMIN") //
                .antMatchers("/web/account.html").permitAll() //
                .antMatchers("/web/transfers.html").hasAnyAuthority("CLIENT","ADMIN") ///web/transfers.html
                .antMatchers("/web/cards.html").hasAnyAuthority("CLIENT","ADMIN") ///web/cards.html
                .antMatchers("/web/create-cards.html").hasAnyAuthority("CLIENT","ADMIN")
                .antMatchers("/web/loan-application.html").hasAnyAuthority("CLIENT","ADMIN")
                .antMatchers("/web/personal-details.html").hasAnyAuthority("CLIENT", "ADMIN")

                .antMatchers("/h2-console").hasAnyAuthority("ADMIN","CLIENT")

                .antMatchers("/rest/**").hasAnyAuthority("CLIENT","ADMIN")
                .antMatchers("/h2-console/**").hasAnyAuthority("ADMIN", "CLIENT")
               .antMatchers("/**").hasAnyAuthority("ADMIN")
                .antMatchers("/web/stats.html").hasAuthority("ADMIN")
               // .antMatchers("/**").hasAuthority("ADMIN");
                .anyRequest().denyAll();


        // Define un recurso POST para hacer el login
        http.formLogin()
                .usernameParameter("email")
                .passwordParameter("password")
                .loginPage("/api/login");

        // Define el recurso para cerrar sesión
        http.logout().logoutUrl("/api/logout");

        // Deshabilita la verificación de tokens CSRF
        http.csrf().disable();

        // Deshabilita las opciones de marco para que se pueda acceder a h2-console
        http.headers().frameOptions().disable();
        // Si el usuario no está autenticado, redirige al usuario a la página de inicio
        http.exceptionHandling().authenticationEntryPoint((req, res, exc) -> res.sendRedirect("/web/index.html"));
        //******************************************************
        // Si el usuario no está autenticado, simplemente envía una respuesta de fallo de autenticación

        //http.exceptionHandling().authenticationEntryPoint((req, res, exc) -> res.sendError(HttpServletResponse.SC_UNAUTHORIZED));

        // Si el inicio de sesión es exitoso, simplemente borra las banderas que piden autenticación
        http.formLogin().successHandler((req, res, auth) -> clearAuthenticationAttributes(req));

        // Si el inicio de sesión falla, simplemente envía una respuesta de fallo de autenticación
        http.formLogin().failureHandler((req, res, exc) -> res.sendError(HttpServletResponse.SC_UNAUTHORIZED));

        // Si el cierre de sesión es exitoso, simplemente envía una respuesta exitosa
        http.logout().logoutSuccessHandler(new HttpStatusReturningLogoutSuccessHandler());
    }

    // Borra los atributos de autenticación de la sesión actual
    private void clearAuthenticationAttributes(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.removeAttribute(WebAttributes.AUTHENTICATION_EXCEPTION);
        }
    }
}


