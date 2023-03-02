package com.programming.techie.apigatewayservice.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.SecurityWebFiltersOrder;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.web.server.WebFilter;

@Configuration
@EnableWebFluxSecurity
@Slf4j
public class SecurityConfig {

    @Bean
    public SecurityWebFilterChain springSecurityFilterChain(ServerHttpSecurity serverHttpSecurity) {
        serverHttpSecurity.authorizeExchange(exchange -> exchange.anyExchange().authenticated())
                .oauth2ResourceServer(ServerHttpSecurity.OAuth2ResourceServerSpec::jwt);

        serverHttpSecurity.csrf().disable();
        serverHttpSecurity.addFilterBefore(authenticationFilter(), SecurityWebFiltersOrder.AUTHENTICATION);
        return serverHttpSecurity.build();
    }

    @Bean
    public WebFilter authenticationFilter() {
        return new WebFilterCustom();
    }
}
