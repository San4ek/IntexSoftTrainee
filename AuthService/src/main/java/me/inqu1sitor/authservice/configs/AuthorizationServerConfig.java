package me.inqu1sitor.authservice.configs;

import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.jwk.source.JWKSource;
import com.nimbusds.jose.proc.SecurityContext;
import lombok.RequiredArgsConstructor;
import me.inqu1sitor.authservice.entities.AccountEntity;
import me.inqu1sitor.authservice.repositories.AccountRepository;
import me.inqu1sitor.authservice.utils.impl.PrivatePropertiesProviderImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;
import org.springframework.security.oauth2.core.OAuth2AccessToken;
import org.springframework.security.oauth2.core.oidc.OidcScopes;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.server.authorization.OAuth2TokenType;
import org.springframework.security.oauth2.server.authorization.client.InMemoryRegisteredClientRepository;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClientRepository;
import org.springframework.security.oauth2.server.authorization.config.annotation.web.configuration.OAuth2AuthorizationServerConfiguration;
import org.springframework.security.oauth2.server.authorization.config.annotation.web.configurers.OAuth2AuthorizationServerConfigurer;
import org.springframework.security.oauth2.server.authorization.settings.AuthorizationServerSettings;
import org.springframework.security.oauth2.server.authorization.settings.ClientSettings;
import org.springframework.security.oauth2.server.authorization.token.JwtEncodingContext;
import org.springframework.security.oauth2.server.authorization.token.OAuth2AccessTokenGenerator;
import org.springframework.security.oauth2.server.authorization.token.OAuth2TokenCustomizer;
import org.springframework.security.web.SecurityFilterChain;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.util.UUID;

/**
 * {@link Configuration} for OAuth 2.0 Authorization Server support.
 *
 * @author Alexander Sankevich
 */
@Configuration(proxyBeanMethods = false)
@RequiredArgsConstructor
public class AuthorizationServerConfig {

    private final PrivatePropertiesProviderImpl privatePropertiesProviderImpl;

    @Bean
    @Order(Ordered.HIGHEST_PRECEDENCE)
    public SecurityFilterChain authorizationServerSecurityFilterChain(HttpSecurity http) throws Exception {
        OAuth2AuthorizationServerConfiguration.applyDefaultSecurity(http);
        http.getConfigurer(OAuth2AuthorizationServerConfigurer.class).oidc(Customizer.withDefaults());
        return http.build();
    }

    @Bean
    @Order(1)
    SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {
        return http.
                csrf(AbstractHttpConfigurer::disable).
                formLogin(Customizer.withDefaults()).
                logout(logout -> logout.logoutUrl("/api/accounts/logout")).
                build();
    }

    @Bean
    public AuthorizationServerSettings providerSettings() {
        return AuthorizationServerSettings.builder().issuer(privatePropertiesProviderImpl.getIssuerUri()).build();
    }

    @Bean
    public RegisteredClientRepository registeredClientRepository(final PasswordEncoder passwordEncoder) {
        RegisteredClient registeredClient = RegisteredClient.withId(UUID.randomUUID().toString()).
                clientId(privatePropertiesProviderImpl.getClientId()).
                clientSecret(passwordEncoder.encode(privatePropertiesProviderImpl.getClientSecret())).
                clientAuthenticationMethod(ClientAuthenticationMethod.CLIENT_SECRET_BASIC).
                authorizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE).
                authorizationGrantType(AuthorizationGrantType.REFRESH_TOKEN).
                redirectUri(privatePropertiesProviderImpl.getRedirectUri()).
                scope(OidcScopes.OPENID).
                scope("auth").
                clientSettings(ClientSettings.builder().requireAuthorizationConsent(false).build()).
                build();

        return new InMemoryRegisteredClientRepository(registeredClient);
    }

    /**
     * Puts the {@link AccountEntity} authorities to {@link OAuth2AccessToken OAuth 2.0 Access Token}.
     *
     * @return {@link OAuth2AccessToken OAuth 2.0 Access Token} customizer, uses in {@link OAuth2AccessTokenGenerator}
     */
    @Bean
    public OAuth2TokenCustomizer<JwtEncodingContext> jwtTokenCustomizer() {
        return context -> {
            if (OAuth2TokenType.ACCESS_TOKEN.equals(context.getTokenType())) {
                context.getClaims().claims(claims -> claims.put("role", AuthorityUtils.authorityListToSet(context.getPrincipal().getAuthorities())));
            }
        };
    }

    @Bean
    UserDetailsService userDetailsService(final AccountRepository accountRepository) {
        return account -> accountRepository.findByEmail(account).orElseThrow(() -> new UsernameNotFoundException(account));
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public JWKSource<SecurityContext> jwkSource() throws NoSuchAlgorithmException {
        RSAKey rsaKey = generateRsa();
        JWKSet jwkSet = new JWKSet(rsaKey);

        return (jwkSelector, securityContext) -> jwkSelector.select(jwkSet);
    }

    private static RSAKey generateRsa() throws NoSuchAlgorithmException {
        KeyPair keyPair = generateRsaKey();

        RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();
        RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();

        return new RSAKey.Builder(publicKey).
                privateKey(privateKey).
                keyID(UUID.randomUUID().toString()).
                build();
    }

    private static KeyPair generateRsaKey() throws NoSuchAlgorithmException {
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
        keyPairGenerator.initialize(2048);

        return keyPairGenerator.generateKeyPair();
    }

    @Bean
    public JwtDecoder jwtDecoder(JWKSource<SecurityContext> jwkSource) {
        return OAuth2AuthorizationServerConfiguration.jwtDecoder(jwkSource);
    }
}