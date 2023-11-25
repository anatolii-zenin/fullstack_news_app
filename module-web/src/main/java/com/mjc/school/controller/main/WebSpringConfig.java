package com.mjc.school.controller.main;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.jdbc.JdbcDaoImpl;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import javax.sql.DataSource;

@Configuration
@ComponentScan({"com.mjc.school.service"})
public class WebSpringConfig {
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(
                requests -> requests.anyRequest().authenticated()
        );
        http.sessionManagement(
                session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        );
        http.httpBasic();
        http.csrf().disable();
        http.headers().frameOptions().sameOrigin();
//        http.oauth2ResourceServer(OAuth2ResourceServerConfigurer::jwt);
        return http.build();
    }

    @Bean
    public DataSource userDataSource() {
        return new EmbeddedDatabaseBuilder()
                .setType(EmbeddedDatabaseType.H2)
                .addScript(JdbcDaoImpl.DEFAULT_USER_SCHEMA_DDL_LOCATION)
                .build();
    }

    @Bean
    public UserDetailsService userDetailsService(DataSource dataSource) {
        var user = User.withUsername("user")
                .password("user")
                .passwordEncoder(str -> passwordEncoder().encode(str))
                .roles("USER")
                .build();

        var admin = User.withUsername("admin")
                .password("admin")
                .passwordEncoder(str -> passwordEncoder().encode(str))
                .roles("ADMIN")
                .build();

        var jdbcUserDetailsManager = new JdbcUserDetailsManager(dataSource);
        jdbcUserDetailsManager.createUser(user);
        jdbcUserDetailsManager.createUser(admin);

        return jdbcUserDetailsManager;
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

//    @Bean
//    public KeyPair keyPair() {
//        KeyPairGenerator keyPairGen = null;
//        try {
//            keyPairGen = KeyPairGenerator.getInstance("RSA");
//        } catch (NoSuchAlgorithmException e) {
//            throw new RuntimeException(e);
//        }
//        keyPairGen.initialize(2048);
//        return keyPairGen.generateKeyPair();
//    }
//
//    @Bean
//    public RSAKey rsaKey(KeyPair keyPair) {
//        return new RSAKey.Builder((RSAPublicKey) keyPair.getPublic())
//                .privateKey(keyPair.getPrivate())
//                .keyID(UUID.randomUUID().toString())
//                .build();
//    }
//
//    @Bean
//    public JWKSource<SecurityContext> jwkSource(RSAKey rsaKey) {
//        var jwkSet = new JWKSet(rsaKey);
//        return (jwkSelector, context) -> jwkSelector.select(jwkSet);
//    }
//
//    @Bean
//    public JwtDecoder jwtDecoder(RSAKey rsaKey) {
//        try {
//            return NimbusJwtDecoder.withPublicKey(rsaKey.toRSAPublicKey())
//                    .build();
//        } catch (JOSEException e) {
//            throw new RuntimeException(e);
//        }
//    }
//
//    @Bean
//    public JwtEncoder jwtEncoder(JWKSource<SecurityContext> jwkSource) {
//        return new NimbusJwtEncoder(jwkSource);
//    }

}
