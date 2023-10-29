package kattyfashion.jwtprovider.config.jwt;

import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;
import kattyfashion.jwtprovider.errors.ErrorMessage;
import kattyfashion.jwtprovider.errors.JwtError;
import lombok.NoArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.UUID;

@Component
@NoArgsConstructor
public class JwtUtils {
    private static final Logger logger = LoggerFactory.getLogger(JwtUtils.class);

    @Value("${kf.app.jwtSecret}")
    private String jwtSecret;

    @Value("${kf.app.jwtAccessExpirationMs}")
    private int jwtAccessExpirationMs;

    @Value("${kf.app.jwtAccessExpirationMs}")
    private int jwtRefreshExpriationMs;
    public String generateAccessJwtToken(String username) {
        return Jwts.builder()
                .id(UUID.randomUUID().toString())
                .subject((username))
                .issuedAt(new Date())
                .expiration(new Date((new Date()).getTime() + jwtAccessExpirationMs))
                .signWith(key(), Jwts.SIG.HS256)
                .compact();


    }

    public String generateRefreshJwtToken(String username) {
        return Jwts.builder()
                .id(UUID.randomUUID().toString())
                .subject((username))
                .issuedAt(new Date())
                .expiration(new Date((new Date()).getTime() + jwtRefreshExpriationMs))
                .signWith(key(), Jwts.SIG.HS256)
                .compact();
    }

    private SecretKey key() {
        return Keys.hmacShaKeyFor(Decoders.BASE64URL.decode(jwtSecret));
    }

    public String getUserNameFromJwtToken(String token) {
        return Jwts.parser().verifyWith(key()).build()
                .parseSignedClaims(token).getPayload().getSubject();
    }

    public boolean validateJwtToken(String authToken) {
        try {
            Jwts.parser().verifyWith(key()).build().parse(authToken);
            return true;
        } catch (MalformedJwtException e) {
            logger.error("Invalid JWT token: {}", e.getMessage());
            throw new JwtError(ErrorMessage.E_03);
        } catch (ExpiredJwtException e) {
            logger.error("JWT token is expired: {}", e.getMessage());
            throw new JwtError(ErrorMessage.E_04);
        } catch (UnsupportedJwtException e) {
            logger.error("JWT token is unsupported: {}", e.getMessage());
            throw new JwtError(ErrorMessage.E_05);
        } catch (IllegalArgumentException e) {
            logger.error("JWT claims string is empty: {}", e.getMessage());
            throw new JwtError(ErrorMessage.E_06);
        }

    }
    public String parseJwt(HttpServletRequest request) {
        String headerAuth = request.getHeader("Authorization");

        if (StringUtils.hasText(headerAuth) && headerAuth.startsWith("Bearer ")) {
            return headerAuth.substring(7);
        }

        return null;
    }

}
