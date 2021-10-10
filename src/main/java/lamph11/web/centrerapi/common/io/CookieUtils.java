package lamph11.web.centrerapi.common.io;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.SessionScope;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

@Component
@SessionScope
@RequiredArgsConstructor
public class CookieUtils {

    private final HttpServletResponse httpServletResponse;

    public void writeCookie(String name, String value, int time) {
        Cookie cookie = new Cookie(name, value);
        cookie.setHttpOnly(false);
        cookie.setMaxAge(time);
        cookie.setPath("/");
        httpServletResponse.addCookie(cookie);
    }
}
