package ch.zhaw.mosltech.NoPainIsGainBackend.security;

import static org.springframework.security.config.Customizer.withDefaults;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

/**
 * Configuration class for setting up HTTP Basic Authentication using Spring Security.
 * <p>
 * This configuration is responsible for securing HTTP requests, configuring form login, 
 * and setting up password encoding. It includes custom security configurations for the application,
 * such as permitting access to specific paths and disabling certain security features for compatibility reasons.
 * </p>
 */
@Configuration
@EnableMethodSecurity(securedEnabled = true)
public class HttpBasicSecurityConfig {

    /**
     * Configures the security filter chain for HTTP requests.
     * <p>
     * Defines which HTTP requests should be authorized or permitted without authentication, such as requests to
     * the H2 database console and error paths. It also configures CSRF protection settings and frame options
     * for the application.
     * </p>
     *
     * @param http The {@link HttpSecurity} to configure.
     * @return The configured {@link SecurityFilterChain}.
     * @throws Exception if an error occurs during the configuration.
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                // Authorize requests configuration
                .authorizeHttpRequests(auth -> {
                    // Permit all requests to H2 console and error paths without authentication
                    auth.requestMatchers(AntPathRequestMatcher.antMatcher("/h2-console/**")).permitAll();
                    auth.requestMatchers(AntPathRequestMatcher.antMatcher("/error/**")).permitAll();
                    // Require authentication for all other requests
                    auth.anyRequest().authenticated();
                })
                .httpBasic(withDefaults())
                // Configure form login with default settings
                .formLogin(withDefaults())
                // CSRF configuration to ignore the H2 console and API paths
                .csrf(csrf -> csrf.ignoringRequestMatchers(AntPathRequestMatcher.antMatcher("/h2-console/**")))
                .csrf(csrf -> csrf.ignoringRequestMatchers(AntPathRequestMatcher.antMatcher("/api/**")))
                // Disable frame options for H2 console compatibility
                .headers(headers -> headers.frameOptions(frameOptions -> frameOptions.disable()))
                .build();
    }
    
    /**
     * Configures the password encoder to be used for encoding passwords in the application.
     * <p>
     * Uses {@link BCryptPasswordEncoder} as the password encoding mechanism, which provides strong
     * hashing and encryption for storing user passwords securely.
     * </p>
     *
     * @return A {@link PasswordEncoder} instance.
     */
    @Bean
    public static PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * Exposes the {@link AuthenticationManager} to the Spring context.
     * <p>
     * Allows the {@link AuthenticationManager} to be autowired and used within the application, 
     * facilitating authentication operations.
     * </p>
     *
     * @param configuration The {@link AuthenticationConfiguration} from which to retrieve the authentication manager.
     * @return The {@link AuthenticationManager} instance.
     * @throws Exception if an error occurs retrieving the authentication manager.
     */
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }
}
