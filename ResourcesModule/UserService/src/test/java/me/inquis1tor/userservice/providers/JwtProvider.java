package me.inquis1tor.userservice.providers;

import com.nimbusds.jose.Algorithm;
import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.JWSHeader;
import com.nimbusds.jose.crypto.RSASSASigner;
import com.nimbusds.jose.jwk.KeyUse;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.jwk.gen.RSAKeyGenerator;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import me.inquis1tor.userservice.entities.AccountEntity;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;

import java.time.Instant;
import java.util.Arrays;
import java.util.Date;
import java.util.stream.Collectors;

public class JwtProvider {

    public static SecurityMockMvcRequestPostProcessors.JwtRequestPostProcessor withJwt(final String accountId, final AccountEntity.Role...roles) {
        return SecurityMockMvcRequestPostProcessors.jwt().jwt(jwt -> jwt.claim("role", Arrays.stream(roles).map(Enum::name).collect(Collectors.toUnmodifiableSet())).subject(accountId));
    }

    public static SecurityMockMvcRequestPostProcessors.JwtRequestPostProcessor withUserJwt(final String accountId) {
        return withJwt(accountId, AccountEntity.Role.USER);       }

    public static SecurityMockMvcRequestPostProcessors.JwtRequestPostProcessor withModerJwt(final String accountId) {
        return withJwt(accountId, AccountEntity.Role.MODER);
    }

    public static SecurityMockMvcRequestPostProcessors.JwtRequestPostProcessor withAdminJwt(final String accountId) {
        return withJwt(accountId, AccountEntity.Role.ADMIN);
    }

    public static RSAKey generateRsaKey() throws JOSEException {
        return new RSAKeyGenerator(2048)
                .keyUse(KeyUse.SIGNATURE)
                .algorithm(new Algorithm("RS256"))
                .generate();
    }

    public static String generateUserJwt(final String accountId, final RSAKey rsaKey) throws Exception {
        return generateSignedJwt(accountId, rsaKey, AccountEntity.Role.USER);
    }

    public static String generateAdminJwt(final String accountId, final RSAKey rsaKey) throws Exception {
        return generateSignedJwt(accountId, rsaKey, AccountEntity.Role.ADMIN);
    }

    public static String generateModerJwt(final String accountId, final RSAKey rsaKey) throws Exception {
        return generateSignedJwt(accountId, rsaKey, AccountEntity.Role.MODER);
    }

    public static String generateSignedJwt(final String accountId, final RSAKey rsaKey, final AccountEntity.Role... roles) throws Exception {
        JWTClaimsSet claimsSet = new JWTClaimsSet.
                Builder().
                expirationTime(Date.from(Instant.now().plusMillis(60*1000))).
                claim("role", roles).
                claim("sub", accountId).
                build();
        SignedJWT signedJWT = new SignedJWT(new JWSHeader.
                Builder(JWSAlgorithm.RS256).
                keyID(rsaKey.getKeyID()).
                build(), claimsSet);
        signedJWT.sign(new RSASSASigner(rsaKey));
        return signedJWT.serialize();
    }
}
