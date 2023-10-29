package kattyfashion.jwtprovider.service;

import kattyfashion.jwtprovider.config.jwt.JwtUtils;
import kattyfashion.jwtprovider.errors.JwtError;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CacheService {
    private final CacheManager cacheManager;
    private final JwtUtils jwtUtils;
    private final RedisTemplate<String, String> redisTemplate;

    public CacheService(JwtUtils jwtUtils, CacheManager cacheManager, RedisTemplate<String, String> redisTemplate) {
        this.cacheManager = cacheManager;
        this.jwtUtils = jwtUtils;
        this.redisTemplate = redisTemplate;
    }
    public List<String> getCachedToken(String username) {
        Cache accessTokenCache = cacheManager.getCache("accessTokenCache");
        Cache refreshTokenCache = cacheManager.getCache("refreshTokenCache");
        Cache tokensLink = cacheManager.getCache("token_links");
        try {
            String accessCachedToken = accessTokenCache.get(username, String.class);
            String refreshCachedToken = refreshTokenCache.get(username, String.class);

            if (accessCachedToken != null && refreshCachedToken != null) {
                if( jwtUtils.validateJwtToken(accessCachedToken)) {
                    return List.of(accessCachedToken, refreshCachedToken);
                }

            }else {
                return getNewTokens(username, accessTokenCache, refreshTokenCache);
            }
        } catch (JwtError e) {
            return List.of(jwtUtils.generateAccessJwtToken(username), jwtUtils.generateRefreshJwtToken(username));
        }

        return null;
    }

    public List<String> getNewTokens(String username, Cache accessTokenCache, Cache refreshTokenCache) {
        String newAccessToken = jwtUtils.generateAccessJwtToken(username);
        String newRefreshToken = jwtUtils.generateRefreshJwtToken(username);
        accessTokenCache.put(username, newAccessToken);
        refreshTokenCache.put(username, newRefreshToken);

        return List.of(newAccessToken, newRefreshToken);
    }

    public void clearCache(String token) {
        String username = jwtUtils.getUserNameFromJwtToken(token);
        Cache tokenCache = cacheManager.getCache("jwtTokens");
        tokenCache.evict(tokenCache);
    }
}
