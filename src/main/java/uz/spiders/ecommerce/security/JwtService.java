package uz.spiders.ecommerce.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import uz.spiders.ecommerce.entity.User;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;

@Service
public class JwtService {
    @Value("${app.jwt.token.expiration.access}")
    private long ACCESS_TOKEN_EXP_PERIOD;
    @Value("${app.jwt.token.expiration.refresh}")
    private long REFRESH_TOKEN_EXP_PERIOD;
    @Value("${app.jwt.token.key.access}")
    private String ACCESS_SECRET_KEY;
    @Value("${app.jwt.token.key.refresh}")
    private String REFRESH_SECRET_KEY;

    @Value("${app.jwt.token.key.verify}")
    private String VERIFY_SECRET_KEY;
    @Value("${app.jwt.token.expiration.verify}")
    private long VERIFY_TOKEN_EXP_PERIOD;

    public String extractUsernameFromAccessToken(String token) {
        return extractClaims(token, Claims::getSubject, ACCESS_SECRET_KEY);
    }

    public String extractUsernameFromRefreshToken(String token) {
        return extractClaims(token, Claims::getSubject, REFRESH_SECRET_KEY);
    }

    public boolean isAccessTokenValid(String token, UserDetails userDetails) {
        String username = extractUsernameFromAccessToken(token);
        return Objects.equals(userDetails.getUsername(), username) && !isAccessTokenExpired(token);
    }

    public boolean isRefreshTokenValid(String token, UserDetails userDetails) {
        String username = extractUsernameFromAccessToken(token);
        return Objects.equals(userDetails.getUsername(), username) && !isRefreshTokenExpired(token);
    }

    private boolean isAccessTokenExpired(String token) {
        return extractClaims(token, Claims::getExpiration, ACCESS_SECRET_KEY).before(new Date());
    }

    private boolean isRefreshTokenExpired(String token) {
        return extractClaims(token, Claims::getExpiration, REFRESH_SECRET_KEY).before(new Date());
    }

    //generic method that can extract particular claim
    private <T> T extractClaims(String token, Function<Claims, T> claimResolver, String key){
        final Claims claims = extractAllClaims(token, key);
        return claimResolver.apply(claims);
    }

    private Claims extractAllClaims(String token, String key) {
        return Jwts
                .parserBuilder()
                .setSigningKey(getSignInKey(key))
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    //to generate only with userDetails
    public String generateAccessToken(UserDetails userDetails){
        return generateToken(new HashMap<>(),userDetails, ACCESS_TOKEN_EXP_PERIOD, ACCESS_SECRET_KEY);
    }

    public String generateRefreshToken(UserDetails userDetails) {
        return generateToken(new HashMap<>(), userDetails, REFRESH_TOKEN_EXP_PERIOD, REFRESH_SECRET_KEY);
    }

    //to generate with extra claims
    public String generateToken(Map<String, Objects> claims, UserDetails userDetails, long exp, String key){
        return Jwts
                .builder()
                .setClaims(claims)
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis()+ exp))
                .signWith(getSignInKey(key), SignatureAlgorithm.HS256)
                .compact();
    }

    private Key getSignInKey(String key) {
        byte[] bytes = Decoders.BASE64.decode(key);
        return Keys.hmacShaKeyFor(bytes);
    }

    //check validation token with email and expiration token
    public boolean isVerificationTokenValid(String email, String token) {
        return Objects.equals(email, extractClaims(token, Claims::getSubject, VERIFY_SECRET_KEY))
                && extractClaims(token, Claims::getExpiration, VERIFY_SECRET_KEY).before(new Date());
    }

    public String generateVerificationToken(User user) {
        return Jwts
                .builder()
                .setClaims(new HashMap<>())
                .setSubject(user.getEmail())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis()+ VERIFY_TOKEN_EXP_PERIOD))
                .signWith(getSignInKey(VERIFY_SECRET_KEY), SignatureAlgorithm.HS256)
                .compact();
    }
}
