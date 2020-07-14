package deposit.security.jwt;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

/**
 * Вспомогательный класс для генерации токена, получения Username из него и валидации.
 */

@Component
public class JwtUtils {
    @Value("${jwt.token.secret}")
    private String jwtSecret;

    public String generateJwtToken(Authentication authentication) {
        JwtUser jwtUser = (JwtUser) authentication.getPrincipal();
        return "Bearer_" + Jwts.builder()
                .setSubject(jwtUser.getUsername())
                .signWith(SignatureAlgorithm.HS512, jwtSecret)
                .compact();
    }

    public String getUsernameFromJwtToken(String token) {
        return Jwts.parser()
                .setSigningKey(jwtSecret)
                .parseClaimsJws(token)
                .getBody().getSubject();
    }

    public boolean validateJwtToken(String authToken) {
        Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(authToken);
        return true;
    }
}
