package de.hska.iwi.vslab.zuul;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.TokenStore;

@SuppressWarnings("deprecation")
@Configuration
@EnableResourceServer
public class ResourceServerConfig extends ResourceServerConfigurerAdapter {
    // private static final String RESOURCE_ID = "webshop-resource";

    // @Override
    // public void configure(ResourceServerSecurityConfigurer security) throws
    // Exception {
    // security.resourceId(RESOURCE_ID);
    // }

    @Override
    public void configure(HttpSecurity http) throws Exception {
        // @formatter:off
        http
                .authorizeRequests()
                .antMatchers(HttpMethod.POST, "/users").permitAll() // allow registration
                .antMatchers("/auth/**").permitAll()
                .anyRequest().authenticated()
                .and().csrf().disable();
        // @formatter:on
    }
}