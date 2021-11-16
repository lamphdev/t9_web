package lamph11.web.centrerapi.config;

import lamph11.web.centrerapi.common.auth.DynamicAuthorizationFilter;
import lamph11.web.centrerapi.common.auth.LphAuthenticationEntrypoint;
import lamph11.web.centrerapi.common.auth.TokenAuthFilter;
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
                autho -> {
                    autho.anyRequest().permitAll();
                    autho.withObjectPostProcessor(new ObjectPostProcessor<FilterSecurityInterceptor>() {
                        @Override
                        public <O extends FilterSecurityInterceptor> O postProcess(O o) {
                            o.setSecurityMetadataSource(dynamicAuthorizationFilter);
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
                }
        );
    }
}
