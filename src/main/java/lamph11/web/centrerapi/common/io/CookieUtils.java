package lamph11.web.centrerapi.common.io;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.RequestScope;

import javax.annotation.PostConstruct;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;

@Slf4j
@Component
@RequestScope
@RequiredArgsConstructor
public class CookieUtils {

    private final HttpServletRequest httpServletRequest;
    private final HttpServletResponse httpServletResponse;

    public void writeCookie(String name, String value, int time) {
        Cookie cookie = new Cookie(name, value);
        // cookie.setDomain(getDomain());
        cookie.setHttpOnly(false);
        cookie.setMaxAge(time);
        cookie.setPath("/");
        httpServletResponse.addCookie(cookie);
    }

    public Cookie getCookie(String name) {
        Cookie[] cookies = httpServletRequest.getCookies();
        if (null == cookies || cookies.length < 1)
            return null;

        Arrays.stream(cookies)
                .map(Cookie::getName)
                .forEach(System.out::println);
        Cookie cookie = Arrays.stream(cookies)
                .filter(c -> c.getName().equals(name))
                .findFirst().orElse(null);
        return cookie;
    }

    public String getDomain() {
        StringBuilder builder = new StringBuilder();
        builder.append(httpServletRequest.getScheme())
                .append("://")
                .append(httpServletRequest.getServerName())
                .append(":")
                .append(httpServletRequest.getServerPort());
        return builder.toString();
    }

    @PostConstruct
    public void test() {
        log.info("server domain: {}", getDomain());
    }
}
