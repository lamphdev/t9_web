package lamph11.web.centrerapi.common.utils;

import lamph11.web.centrerapi.resources.dto.auth.UserInfoDTO;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public class SecurityUtils {

    public static UserInfoDTO getUserLogin() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (null == authentication || authentication instanceof AnonymousAuthenticationToken) {
            return null;
        }

        return (UserInfoDTO) authentication;
    }
}
