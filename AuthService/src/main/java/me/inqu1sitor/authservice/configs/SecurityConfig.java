package me.inqu1sitor.authservice.configs;

import me.inqu1sitor.authservice.entities.Account;
import me.inqu1sitor.authservice.repositories.AccountRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@EnableWebSecurity
@Configuration(proxyBeanMethods = false)
public class SecurityConfig {

    @Bean
    SecurityFilterChain restApiSecurityFilterChain(HttpSecurity http) throws Exception {
        return http.
                securityMatcher("/api/**").
                csrf(csrf -> csrf.disable()).
                authorizeHttpRequests(authorize ->
                        authorize.
                                requestMatchers(HttpMethod.POST,"api/accounts/user").permitAll().
                                requestMatchers(HttpMethod.DELETE,"/api/accounts").authenticated().
                                requestMatchers(HttpMethod.PUT,"/api/accounts").authenticated().
                                requestMatchers("api/accounts/**").hasAuthority("ROLE_"+ Account.Role.ADMIN.name())).
                logout(logout -> logout.logoutUrl("/logout")).
                sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)).
                oauth2ResourceServer(oauth2 ->
                        oauth2.jwt(Customizer.withDefaults())).
                build();
    }

    @Bean
    SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {
        return http.formLogin(Customizer.withDefaults()).build();
    }

    @Bean
    UserDetailsService userDetailsService(AccountRepository accountRepository) {
        return account -> accountRepository.findByEmail(account).orElseThrow(() -> new UsernameNotFoundException(account));
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
