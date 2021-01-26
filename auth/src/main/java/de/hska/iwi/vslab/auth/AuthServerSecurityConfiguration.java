package de.hska.iwi.vslab.auth;

import de.hska.iwi.vslab.auth.UserDetailService;
import com.nimbusds.jose.jwk.JWK;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;

import org.springframework.security.core.userdetails.User.UserBuilder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.oauth2.provider.token.TokenStore;

import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.interfaces.RSAPublicKey;

import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.jwk.KeyUse;

@EnableWebSecurity
public class AuthServerSecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests().antMatchers("/oauth2/keys").permitAll().anyRequest().authenticated().and().formLogin();
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Bean
    @SuppressWarnings("deprecation")
    public TokenStore tokenStore() {
        return new JwtTokenStore(accessTokenConverter());
    }

    @Bean
    @SuppressWarnings("deprecation")
    public JwtAccessTokenConverter accessTokenConverter() {
        JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
        converter.setSigningKey("strong-secret");
        return converter;
    }

    // @Bean
    // @SuppressWarnings("deprecation")
    // public UserDetailsService users() {
    // UserBuilder users = User.withDefaultPasswordEncoder();
    // InMemoryUserDetailsManager manager = new InMemoryUserDetailsManager();
    // manager.createUser(users.username("user").password("password").roles("USER").build());
    // manager.createUser(users.username("admin").password("password").roles("USER",
    // "ADMIN").build());
    // return manager;
    // }

    @Bean
    public UserDetailService getUserDetailsService() {
        return new UserDetailService();
    }

    @Bean
    public JWKSet jwkSet() throws NoSuchAlgorithmException {
        KeyPairGenerator gen = KeyPairGenerator.getInstance("RSA");
        gen.initialize(2048);
        KeyPair keyPair = gen.generateKeyPair();

        RSAKey.Builder jwk = new RSAKey.Builder((RSAPublicKey) keyPair.getPublic()).keyUse(KeyUse.SIGNATURE)
                .algorithm(JWSAlgorithm.RS256).keyID("key-id");
        return new JWKSet(jwk.build());
    }
}
