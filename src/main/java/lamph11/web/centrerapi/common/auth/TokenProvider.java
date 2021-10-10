package lamph11.web.centrerapi.common.auth;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Component
public class TokenProvider {

    public static final String SECRET = "7T8;xCJN/Xb.t<4";
    public static final long EXPIRE_TIME = 6 * 60 * 60 * 1000;

    public Authentication parseToken(String token) {
        Jws<Claims> jwt = Jwts.parser().setSigningKey(SECRET).parseClaimsJws(token);
        log.info("subject {}", jwt.getBody().getSubject());
        String username = jwt.getBody().getSubject();
        List<String> roles = jwt.getBody().get("roles", List.class);
        return new UsernamePasswordAuthenticationToken(
                username,
                null,
                roles.stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList())
        );
    }


    public String generateToken(Authentication authentication) {
        Long timeNow = System.currentTimeMillis();
        return Jwts.builder().signWith(SignatureAlgorithm.HS512, SECRET)
                .setIssuedAt(new Date(timeNow))
                .setExpiration(new Date(timeNow + EXPIRE_TIME))
                .claim("roles", authentication.getAuthorities())
                .setSubject(authentication.getName())
                .compact();
    }
}
