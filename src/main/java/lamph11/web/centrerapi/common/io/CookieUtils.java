package lamph11.web.centrerapi.common.io;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.RequestScope;

import javax.annotation.PostConstruct;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
@RequestScope
@RequiredArgsConstructor
public class CookieUtils {

    private final HttpServletRequest httpServletRequest;
    private final HttpServletResponse httpServletResponse;

    public void writeCookie(String name, String value, int time) {
        Cookie cookie = new Cookie(name, value);
        cookie.setDomain(getDomain());
        cookie.setHttpOnly(false);
        cookie.setMaxAge(time);
        cookie.setPath("/");
        httpServletResponse.addCookie(cookie);
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
        System.out.println("testday: " + getDomain());
    }
}
