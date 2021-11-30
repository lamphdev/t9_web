package lamph11.web.centrerapi.common.auth;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
public class LphAccessDecisionManager implements AccessDecisionManager {

    @Override
    public void decide(Authentication authentication, Object o, Collection<ConfigAttribute> collection) throws AccessDeniedException, InsufficientAuthenticationException {
        Set<String> available = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toSet());

        for (ConfigAttribute attribute : collection) {
            if (available.contains(attribute.getAttribute())) {
                return;
            }
        }

        String collectionString = collection.toString();
        String message = "you need " + String.valueOf(collectionString) + " for this resource";
        log.info(message);
        throw new AccessDeniedException(message);
    }

    @Override
    public boolean supports(ConfigAttribute configAttribute) {
        return StringUtils.isNoneEmpty(configAttribute.getAttribute());
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return true;
    }
}
