package lamph11.web.centrerapi.common.auth;

import io.jsonwebtoken.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Slf4j
@Component
public class TokenProvider {

    public static final String SECRET = "7T8;xCJN/Xb.t<4";
    public static final long EXPIRE_TIME = 6 * 60 * 60 * 1000;

    public Authentication parseToken(String token) {
        Jws<Claims> jwt = Jwts.parser().setSigningKey(SECRET).parseClaimsJws(token);
        String username = jwt.getBody().getSubject();
        SecurityContextHolder.getContext().getAuthentication().getName();
        String[] roles = jwt.getBody().get("roles", String[].class);
        return new UsernamePasswordAuthenticationToken(
                username,
                null,
                Stream.of(roles).map(SimpleGrantedAuthority::new).collect(Collectors.toList())
        );
    }


    public String generateToken(Authentication authentication) {
        Long timeNow = System.currentTimeMillis();
        return Jwts.builder().signWith(SignatureAlgorithm.HS512, SECRET)
                .setIssuedAt(new Date(timeNow))
                .setExpiration(new Date(timeNow + EXPIRE_TIME))
                .claim("roles", authentication.getAuthorities().toArray(new String[] {}))
                .setSubject(authentication.getName())
                .compact();
    }
}
