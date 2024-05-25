package com.openwebinars.rest.configurations;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.openwebinars.rest.service.UserDetailsServiceImpl;
import java.lang.Override;

@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    private UserDetailsServiceImpl userDetailsService;
    
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
            .authorizeRequests()
                .antMatchers("/api/v1/login", "/login/**", "/css/**", "/js/**").permitAll() // Permitir acceso a /api/v1/login y otras rutas sin autenticación
                .anyRequest().authenticated()
                .and()
            .formLogin()
                .loginPage("/login") // Especificar la ruta de la página de inicio de sesión personalizada
                .defaultSuccessUrl("/") // Ruta a la que redirigir después del inicio de sesión exitoso
                .permitAll()
                .and()
            .logout()
                .permitAll();
    }


    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}

