package lamph11.web.centrerapi.common.auth;

import lamph11.web.centrerapi.resources.dto.api_rule.ApiRuleDTO;
import lamph11.web.centrerapi.service.ApiRuleService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class DynamicAuthorizationFilter implements FilterInvocationSecurityMetadataSource {

    private final ApiRuleService apiRuleService;
    private final AntPathMatcher antPathMatcher = new AntPathMatcher();

    @Override
    public Collection<ConfigAttribute> getAttributes(Object o) throws IllegalArgumentException {
        FilterInvocation filterInvocation = (FilterInvocation) o;
        String url = filterInvocation.getRequestUrl();
        List<String> needRole = findNeedRole(url);
        log.info("You need role {} for url: {}", needRole, url);
        return SecurityConfig.createList(needRole.toArray(new String[]{}));
    }

    @Override
    public Collection<ConfigAttribute> getAllConfigAttributes() {
        return null;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return FilterInvocation.class.isAssignableFrom(aClass);
    }

    private List<String> findNeedRole(String url) {
        List<ApiRuleDTO> apiRules = apiRuleService.query(null);
        for (ApiRuleDTO apiRule : apiRules) {
            if (antPathMatcher.match(apiRule.getUrlPattern(), url)) {
                return apiRule.getRoles();
            }
        }
        return Collections.emptyList();
    }
}
