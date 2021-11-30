package lamph11.web.centrerapi.config;

import lamph11.web.centrerapi.common.auth.*;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.ObjectPostProcessor;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final TokenAuthFilter tokenAuthFilter;
    private final DynamicAuthorizationFilter dynamicAuthorizationFilter;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable();

        http.headers().frameOptions().sameOrigin();

        http.authorizeRequests(
                auth -> {
                    auth.anyRequest().permitAll();
                    auth.withObjectPostProcessor(new ObjectPostProcessor<FilterSecurityInterceptor>() {
                        @Override
                        public <O extends FilterSecurityInterceptor> O postProcess(O o) {
                            o.setSecurityMetadataSource(dynamicAuthorizationFilter);
                            o.setAccessDecisionManager(new LphAccessDecisionManager());
                            return o;
                        }
                    });
                }
        );


        http.addFilterBefore(
                tokenAuthFilter,
                UsernamePasswordAuthenticationFilter.class
        );

        http.sessionManagement(
                sessionConfig -> {
                    sessionConfig.sessionCreationPolicy(SessionCreationPolicy.STATELESS);
                }
        );

        http.exceptionHandling(
                handler -> {
                    handler.authenticationEntryPoint(new LphAuthenticationEntrypoint());
                    handler.accessDeniedHandler(new LphAccessDeniedHandler());
                }
        );
    }
}
