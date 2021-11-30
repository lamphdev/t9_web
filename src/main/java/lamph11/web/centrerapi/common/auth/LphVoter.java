package lamph11.web.centrerapi.common.auth;

import org.apache.commons.lang3.StringUtils;
import org.springframework.security.access.AccessDecisionVoter;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.core.Authentication;

import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

public class LphVoter implements AccessDecisionVoter<Object> {

    @Override
    public boolean supports(ConfigAttribute configAttribute) {
        return StringUtils.isNotEmpty(configAttribute.getAttribute());
    }

    @Override
    public int vote(Authentication authentication, Object configAttribute, Collection<ConfigAttribute> attributes) {
        Set<String> available = authentication.getAuthorities().stream()
                .map(String::valueOf).collect(Collectors.toSet());

        for (ConfigAttribute attribute: attributes) {
            if (available.contains(attribute.getAttribute())) {
                return AccessDecisionVoter.ACCESS_GRANTED;
            }
        }
        return AccessDecisionVoter.ACCESS_ABSTAIN;
    }


    @Override
    public boolean supports(Class aClass) {
        return true;
    }
}
