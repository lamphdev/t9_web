package lamph11.web.centrerapi.common.auth;

import lamph11.web.centrerapi.common.io.CookieUtils;
import lamph11.web.centrerapi.service.AuthService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

@Slf4j
@Component
@RequiredArgsConstructor
public class TokenAuthFilter extends OncePerRequestFilter {

    private final CookieUtils cookieUtils;
    private final TokenProvider tokenProvider;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain chain) throws ServletException, IOException {
        try {
            String token;
            Cookie cookie = Optional.ofNullable(cookieUtils.getCookie(AuthService.TOKEN_COOKIE_NAME)).orElse(null);
            if (null != cookie) {
                token = cookie.getValue();
            } else {
                token = request.getHeader(HttpHeaders.AUTHORIZATION);
            }
            if (!StringUtils.isEmpty(token)) {
                log.info("Validating token: {}", token);
                String accessToken = token.replace("Bearer ", "").trim();
                Authentication authentication = tokenProvider.parseToken(accessToken);
                SecurityContextHolder.getContext().setAuthentication(authentication);
            } else {
                log.info("Request without token");
            }
        } catch (Exception e) {
            log.error("Validate Token Error", e.getMessage());
        }
        chain.doFilter(request, response);
    }
}
