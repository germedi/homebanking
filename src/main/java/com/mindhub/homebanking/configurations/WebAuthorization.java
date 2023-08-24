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
        http.authorizeRequests()
                // Permite el acceso a ciertas rutas a todos los usuarios
                .antMatchers("web/index.html", "web/style.css", "web/log.js", "web/img/**", "/api/login").permitAll()
                // Permite el acceso a ciertas rutas a todos los usuarios que hagan una solicitud POST a /api/clients o /api/login
                .antMatchers(HttpMethod.POST,  "/api/login").permitAll()
                // Permite el acceso a ciertas rutas a usuarios con el rol "CLIENT" que hagan una solicitud POST a /api/clients/current/account o /api/transactions
                .antMatchers(HttpMethod.POST, "/api/clients", "/api/clients/current/account", "/api/transactions").hasAuthority("ADMIN")
                // Permite el acceso a ciertas rutas a usuarios con el rol "CLIENT" que hagan una solicitud DELETE a /api/clients/current/cards/**
                .antMatchers(HttpMethod.DELETE, "/api/clients/current/cards/**").hasAuthority("CLIENT")
                // Permite el acceso a ciertas rutas a usuarios con el rol "ADMIN" que hagan una solicitud GET a /manager.html, /manager.js, /rest/** o /api/clients
                .antMatchers("web/manager.html", "web/manager.js", "/rest/**", "/api/clients").hasAuthority("ADMIN");

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

        // Si el usuario no está autenticado, simplemente envía una respuesta de fallo de autenticación
        http.exceptionHandling().authenticationEntryPoint((req, res, exc) -> res.sendError(HttpServletResponse.SC_UNAUTHORIZED));

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


