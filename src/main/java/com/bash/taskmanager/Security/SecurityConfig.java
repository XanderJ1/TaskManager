package com.bash.taskmanager.Security;

import com.bash.taskmanager.Service.UserService;
import com.nimbusds.jose.jwk.*;
import com.nimbusds.jose.jwk.source.*;
import com.nimbusds.jose.proc.SecurityContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.*;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
import org.springframework.security.web.SecurityFilterChain;
import com.bash.taskmanager.Utilities.RSAKeyProperties;


/**
 * Handles all web security operations.
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final RSAKeyProperties keys ;

    SecurityConfig(RSAKeyProperties keys ){
        this.keys = keys;
    }

    JwtAuthenticationFilter jwtAuthFilter;

    /**
     * Provides a BcryptEncoder to encode all passwords.
     * @return a BCryptPasswordEncoder instance.
     */
    @Bean
    PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    /**
     * Provides a Dao Authentication provider sets userDetails with userService and
     * encodes user passwords with the password encoder.
     * @return DaoAuthenticationProvider instance.
     */
    AuthenticationProvider authenticationProvider(UserService userService){
        DaoAuthenticationProvider daoProvider = new DaoAuthenticationProvider();
        daoProvider.setUserDetailsService(userService);
        daoProvider.setPasswordEncoder(passwordEncoder());
        return daoProvider;
    }

    /**
     * Sets up the authentication manager with a Dao Authentication provider.
     * @return authentication manager.
     */
    @Bean
    public AuthenticationManager authManager(UserService userService){
        AuthenticationProvider authProvider = authenticationProvider(userService);
        return new ProviderManager(authProvider);
    }

    /**
     * Sets up security a filter-chain that disables csrf and exposes all css,
     * javascript,and images.
     * It also exposes all endpoints with a /web, /auth, and /tasks prefix.
     * All other endpoints must be authenticated to be accessed.
     * It also uses oauth2 with jwt for authentication.
     * @param http
     * @return a security filter chain
     * @throws Exception
     */
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth -> {
                    auth.requestMatchers("/**").permitAll();
                    auth.requestMatchers("/auth/**").permitAll();
                    auth.requestMatchers("/web/**").permitAll();
                    auth.requestMatchers("/css/**","/tasks/**", "/js/**","/index/**", "/images/**").permitAll();
                    auth.anyRequest().authenticated();
                })
                .oauth2ResourceServer(oauth2 -> oauth2.jwt(jwt -> jwt
                        .decoder(jwtDecoder())
                        .jwtAuthenticationConverter(jwtAuthenticationConverter())
                ))
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .build();

    }

    @Bean
    public JwtDecoder jwtDecoder(){
        return NimbusJwtDecoder.withPublicKey(keys.getPublicKey()).build();
    }

    @Bean
    public JwtEncoder jwtEncoder(){
        JWK jwk = new RSAKey.Builder(keys.getPublicKey()).privateKey(keys.getPrivateKey()).build();
        JWKSource<SecurityContext> jwks = new ImmutableJWKSet<>(new JWKSet(jwk));
        return new NimbusJwtEncoder(jwks);
    }

    /**
     * Convert roles in JWT to a format that is compatible with spring security.
     * @return a JwtAuthentication.
     */
    @Bean
    public JwtAuthenticationConverter jwtAuthenticationConverter(){
        JwtGrantedAuthoritiesConverter jwtGrantedAuthoritiesConverter = new JwtGrantedAuthoritiesConverter();
        jwtGrantedAuthoritiesConverter.setAuthoritiesClaimName("roles");
        jwtGrantedAuthoritiesConverter.setAuthorityPrefix("ROLE_");
        JwtAuthenticationConverter jwt = new JwtAuthenticationConverter();
        jwt.setJwtGrantedAuthoritiesConverter(jwtGrantedAuthoritiesConverter);
        return jwt;
    }
}