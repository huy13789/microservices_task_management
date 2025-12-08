package org.example.apigateway.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.*;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;
import reactor.core.publisher.Mono;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;



@EnableWebFluxSecurity
@Configuration
public class SecurityConfig {

    // Convert roles from realm_access.roles -> ROLE_XXX
//    private Converter<Jwt, Collection<GrantedAuthority>> jwtToAuthoritiesConverter() {
//        return jwt -> {
//            Object realmAccess = jwt.getClaim("realm_access");
//            if (realmAccess instanceof java.util.Map) {
//                var roles = (java.util.Map<String, Object>) realmAccess;
//                Object ro = roles.get("roles");
//                if (ro instanceof List) {
//                    return ((List<?>) ro).stream()
//                            .map(Object::toString)
//                            .map(r -> new SimpleGrantedAuthority("ROLE_" + r))
//                            .collect(Collectors.toList());
//                }
//            }
//            return List.of();
//        };
//    }

//    @Bean
//    public SecurityWebFilterChain springSecurityFilterChain(ServerHttpSecurity http) {
//        JwtAuthenticationConverter jwtConverter = new JwtAuthenticationConverter();
//        jwtConverter.setJwtGrantedAuthoritiesConverter(jwtToAuthoritiesConverter());
//
//        var reactiveJwtAuthConverter = new ReactiveJwtAuthenticationConverterAdapter(jwtConverter);
//
//        return http
//                .csrf(csrf -> csrf.disable())
//                .authorizeExchange(auth -> auth
//                        .pathMatchers("/actuator/**", "/public/**", "/eureka/**").permitAll()
//                        .pathMatchers("/admin/**").hasRole("ADMIN")
//                        .anyExchange().authenticated()
//                )
//                .oauth2ResourceServer(oauth2 -> oauth2
//                        .jwt(jwt -> jwt.jwtAuthenticationConverter(reactiveJwtAuthConverter)))
//                .build();
//    }

    @Bean
    public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http) {

        return http
                .csrf(ServerHttpSecurity.CsrfSpec::disable)
                .authorizeExchange(exchange -> exchange
                        .pathMatchers("/actuator/**").permitAll()
                        .pathMatchers("/public/**").permitAll()
                        .pathMatchers("/eureka/**").permitAll()
                        .anyExchange().authenticated()
                )
                .oauth2ResourceServer(oauth2 ->
                        oauth2.jwt(jwt -> {})
                )
                .build();
    }
}
