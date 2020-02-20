package task.security.jwt;

import static task.constants.AppConstant.UNABLE_TO_GET_JWT_TOKEN;
import static task.constants.ErrorMessage.EXPIRED_OR_INVALID_JWT_TOKEN;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwt;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.impl.DefaultJwtParser;
import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;
import java.util.UUID;
import java.util.function.Function;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import task.entity.User;
import task.exception.BadTokenException;

@Component
public class JwtTool implements Serializable {

    private final Integer accessTokenValidTimeInMinutes;
    private final Integer refreshTokenValidTimeInMinutes;
    private final String accessTokenKey;

    @Autowired
    public JwtTool(
        @Value("${jwt.accessTokenValidTimeInMinutes}") Integer accessTokenValidTimeInMinutes,
        @Value("${jwt.refreshTokenValidTimeInMinutes}") Integer refreshTokenValidTimeInMinutes,
        @Value("${jwt.tokenKey}") String accessTokenKey) {
        this.accessTokenValidTimeInMinutes = accessTokenValidTimeInMinutes;
        this.refreshTokenValidTimeInMinutes = refreshTokenValidTimeInMinutes;
        this.accessTokenKey = accessTokenKey;
    }

    public String getUsernameFromToken(String token) {
        try {
            return getClaimFromToken(token, Claims::getSubject);
        } catch (IllegalArgumentException | ExpiredJwtException e) {
            throw new IllegalArgumentException(UNABLE_TO_GET_JWT_TOKEN, e);
        }
    }

    private Date getExpirationDateFromToken(String token) {
        return getClaimFromToken(token, Claims::getExpiration);
    }

    private <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = getAllClaimsFromToken(token);
        return claimsResolver.apply(claims);
    }

    private Claims getAllClaimsFromToken(String token) {
        return Jwts.parser().setSigningKey(accessTokenKey).parseClaimsJws(token).getBody();
    }

    private Boolean isTokenExpired(String token) {
        final Date expiration = getExpirationDateFromToken(token);
        return expiration.before(new Date());
    }

    public String createAccessToken(String email) {
        Claims claims = Jwts.claims().setSubject(email);
        Date now = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(now);
        calendar.add(Calendar.MINUTE, accessTokenValidTimeInMinutes);
        return Jwts.builder()
            .setClaims(claims)
            .setIssuedAt(now)
            .setExpiration(calendar.getTime())
            .signWith(SignatureAlgorithm.HS256, accessTokenKey)
            .compact();
    }

    public String createRefreshToken(User user) {
        Claims claims = Jwts.claims().setSubject(user.getEmail());
        Date now = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(now);
        calendar.add(Calendar.MINUTE, refreshTokenValidTimeInMinutes);
        return Jwts.builder()
            .setClaims(claims)
            .setIssuedAt(now)
            .setExpiration(calendar.getTime())
            .signWith(SignatureAlgorithm.HS256, user.getRefreshTokenKey())
            .compact();
    }

    public Boolean isTokenValid(String token, UserDetails userDetails) {
        final String username = getUsernameFromToken(token);
        return ((username.equals(userDetails.getUsername())) && (!isTokenExpired(token)));
    }

    public boolean isTokenValid(String token, String tokenKey) {
        boolean isValid = false;
        try {
            Jwts.parser().setSigningKey(tokenKey).parseClaimsJws(token);
            isValid = true;
        } catch (Exception e) {
            throw new BadTokenException(EXPIRED_OR_INVALID_JWT_TOKEN + e);
        }
        return isValid;
    }

    public String getEmailOutOfAccessToken(String token) {
        String[] splitToken = token.split("\\.");
        String unsignedToken = splitToken[0] + "." + splitToken[1] + ".";
        DefaultJwtParser parser = new DefaultJwtParser();
        Jwt<?, ?> jwt = parser.parse(unsignedToken);
        return ((Claims) jwt.getBody()).getSubject();
    }

    public String generateTokenKey() {
        return UUID.randomUUID().toString();
    }
}