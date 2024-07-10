package me.inqu1sitor.authservice.services.impl;

import lombok.RequiredArgsConstructor;
import me.inqu1sitor.authservice.services.ext.OAuth2AuthorizationServiceExt;
import me.inqu1sitor.authservice.utils.RedisPrefixes;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.lang.Nullable;
import org.springframework.security.oauth2.core.OAuth2AccessToken;
import org.springframework.security.oauth2.core.OAuth2RefreshToken;
import org.springframework.security.oauth2.core.OAuth2Token;
import org.springframework.security.oauth2.server.authorization.OAuth2Authorization;
import org.springframework.security.oauth2.server.authorization.OAuth2AuthorizationCode;
import org.springframework.security.oauth2.server.authorization.OAuth2AuthorizationService;
import org.springframework.security.oauth2.server.authorization.OAuth2TokenType;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.Principal;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * A Redis implementation of an {@link OAuth2AuthorizationService} that uses a
 * {@link RedisTemplate} for {@link OAuth2Authorization} persistence.
 *
 * @author Alexander Sankevich
 */
@Service
@RequiredArgsConstructor
public class RedisOAuth2AuthorizationServiceExtImpl implements OAuth2AuthorizationServiceExt {

    private final RedisTemplate<String, Object> redisTemplate;

    @Override
    @Transactional
    public void save(final OAuth2Authorization authorization) {
        redisTemplate.opsForValue().set(RedisPrefixes.ID_FOR_AUTH + authorization.getId(), authorization, 1, TimeUnit.HOURS);
        if (isComplete(authorization)) {
            redisTemplate.delete(RedisPrefixes.CODE_FOR_ID + getCodeValue(authorization));
            redisTemplate.opsForValue().set(RedisPrefixes.ACCESS_FOR_ID + getAccessTokeValue(authorization), authorization.getId(), 5, TimeUnit.MINUTES);
            redisTemplate.opsForValue().set(RedisPrefixes.REFRESH_FOR_ID + getRefreshTokeValue(authorization), authorization.getId(), 1, TimeUnit.HOURS);
            redisTemplate.opsForSet().add(RedisPrefixes.PRINCIPAL_FOR_ID + authorization.getPrincipalName(), authorization.getId());
        } else {
            redisTemplate.opsForValue().set(RedisPrefixes.CODE_FOR_ID + getCodeValue(authorization), authorization.getId(), 5, TimeUnit.MINUTES);
        }
    }

    @Override
    @Transactional
    public void remove(final OAuth2Authorization authorization) {
        redisTemplate.opsForSet().remove(RedisPrefixes.PRINCIPAL_FOR_ID + authorization.getPrincipalName(), authorization.getId());
        redisTemplate.delete(List.of(RedisPrefixes.ID_FOR_AUTH + authorization.getId(),
                RedisPrefixes.CODE_FOR_ID + getCodeValue(authorization),
                RedisPrefixes.ACCESS_FOR_ID + getAccessTokeValue(authorization),
                RedisPrefixes.REFRESH_FOR_ID + getRefreshTokeValue(authorization)));
    }

    @Nullable
    @Override
    @Transactional(readOnly = true)
    public OAuth2Authorization findById(final String id) {
        return (OAuth2Authorization) redisTemplate.opsForValue().get(RedisPrefixes.ID_FOR_AUTH + id);
    }

    @Nullable
    @Override
    @Transactional(readOnly = true)
    public OAuth2Authorization findByToken(final String token, final OAuth2TokenType tokenType) {
        return switch (tokenType != null ? tokenType.getValue() : "null") {
            case "code" -> findByTokenKey(RedisPrefixes.CODE_FOR_ID + token);
            case "access_token" -> findByTokenKey(RedisPrefixes.ACCESS_FOR_ID + token);
            case "refresh_token" -> findByTokenKey(RedisPrefixes.REFRESH_FOR_ID + token);
            default -> null;
        };
    }

    /**
     * Removes the {@link OAuth2Authorization authorization} identified by the provided parameter {@code tokenValue}.
     *
     * @param tokenValue the {@link OAuth2Authorization authorizations} {@link OAuth2AccessToken OAuth 2.0 Access Token} value.
     */
    @Override
    @Transactional
    public void removeByTokenValue(final String tokenValue) {
        OAuth2Authorization authorization;
        if ((authorization = findByToken(tokenValue, OAuth2TokenType.ACCESS_TOKEN)) != null) {
            remove(authorization);
        }
    }

    /**
     * Removes the {@link OAuth2Authorization authorization} identified by the provided parameter {@code principalName}.
     *
     * @param principalName the {@link OAuth2Authorization authorizations} {@link Principal principal} name.
     */
    @Override
    @Transactional
    public void removeByPrincipalName(final String principalName) {
        Set<Object> idSet = redisTemplate.opsForSet().members(RedisPrefixes.PRINCIPAL_FOR_ID + principalName);
        if (idSet != null) {
            for (Object id : idSet) {
                OAuth2Authorization authorization;
                if ((authorization = findById((String) id)) != null) {
                    remove(authorization);
                }
            }
        }
    }

    @Nullable
    private OAuth2Authorization findByTokenKey(final String tokenKey) {
        return findById(findIdBuyTokenKey(tokenKey));
    }

    @Nullable
    private String findIdBuyTokenKey(final String tokenKey) {
        return (String) redisTemplate.opsForValue().get(tokenKey);
    }

    @Nullable
    private String getAccessTokeValue(final OAuth2Authorization authorization) {
        return getTokenValue(authorization, OAuth2AccessToken.class);
    }

    @Nullable
    private String getRefreshTokeValue(final OAuth2Authorization authorization) {
        return getTokenValue(authorization, OAuth2RefreshToken.class);
    }

    @Nullable
    private String getCodeValue(final OAuth2Authorization authorization) {
        return getTokenValue(authorization, OAuth2AuthorizationCode.class);
    }

    @Nullable
    private <T extends OAuth2Token> String getTokenValue(final OAuth2Authorization authorization, final Class<T> tokenType) {
        OAuth2Authorization.Token<T> token;
        return (token = authorization.getToken(tokenType)) != null ? token.getToken().getTokenValue() : null;
    }

    private boolean isComplete(final OAuth2Authorization authorization) {
        return authorization.getAccessToken() != null;
    }
}
