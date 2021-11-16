package lamph11.web.centrerapi.common.utils;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.RequestScope;

import javax.servlet.http.HttpServletRequest;

@Slf4j
@Component
@RequestScope
@RequiredArgsConstructor
public class RequestUtils {

    private final HttpServletRequest httpServletRequest;

    public <T> T getAttribute(String attributeName) {
        return (T) httpServletRequest.getAttribute(attributeName);
    }
}
