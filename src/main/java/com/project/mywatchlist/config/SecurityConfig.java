package com.project.mywatchlist.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/css/**", "/js/**", "/images/**", "/webjars/**", "/favicon.ico").permitAll() // Recursos estáticos
                        .requestMatchers("/filmes/novo").authenticated() // Requer login
                        .requestMatchers("/filmes/*/editar").authenticated() // Requer login - PATTERN CORRIGIDO
                        .requestMatchers("/filmes/*/deletar").authenticated() // Requer login - PATTERN CORRIGIDO
                        .anyRequest().permitAll() // Outros endpoints públicos
                )
                .formLogin(form -> form
                        .loginPage("/login") // Página de login personalizada
                        .loginProcessingUrl("/login") // URL para processar login
                        .defaultSuccessUrl("/filmes", true) // Redireciona após login
                        .failureUrl("/login?error=true") // Redireciona se falhar
                        .usernameParameter("username") // Nome do campo de usuário
                        .passwordParameter("password") // Nome do campo de senha
                        .permitAll()
                )
                .logout(logout -> logout
                        .logoutUrl("/logout") // URL para logout
                        .logoutSuccessUrl("/filmes?logout=true") // Redireciona após logout
                        .invalidateHttpSession(true) // Invalida sessão
                        .deleteCookies("JSESSIONID") // Deleta cookies
                        .permitAll()
                )
                .csrf(csrf -> csrf.disable()); // Desabilita CSRF para simplificar (em produção, mantenha ativado!)

        return http.build();
    }

    @Bean
    public UserDetailsService userDetailsService() {
        // Usuário padrão: user / password
        UserDetails user = User.builder()
                .username("user")
                .password("{noop}password") // {noop} = sem codificação (em produção, use BCrypt!)
                .roles("USER")
                .build();

        // Você pode adicionar mais usuários aqui
        UserDetails admin = User.builder()
                .username("admin")
                .password("{noop}admin123")
                .roles("ADMIN", "USER")
                .build();

        return new InMemoryUserDetailsManager(user, admin);
    }
}
