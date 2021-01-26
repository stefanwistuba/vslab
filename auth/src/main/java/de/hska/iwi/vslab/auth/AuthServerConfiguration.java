package de.hska.iwi.vslab.auth;

import org.springframework.context.annotation.Configuration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.provider.token.store.InMemoryTokenStore;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
//import org.springframework.security.core.userdetails.UserDetailsService;
import de.hska.iwi.vslab.auth.UserDetailService;

@Configuration
@EnableAuthorizationServer
public class AuthServerConfiguration extends AuthorizationServerConfigurerAdapter {

    @Autowired
    @Qualifier("authenticationManagerBean")
    private AuthenticationManager authenticationManager;

    @Autowired
    @Qualifier("tokenStore")
    private TokenStore tokenStore;

    @Autowired
    @Qualifier("accessTokenConverter")
    private JwtAccessTokenConverter accessTokenConverter;

    @Autowired
    @Qualifier("getUserDetailsService")
    private UserDetailService userDetailsService;

    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        clients.inMemory().withClient("webshop-client").secret("strong")
                .authorizedGrantTypes("authorization_code", "password", "refresh_token")
                .redirectUris("http://zuul:8081/client/authorized").scopes("message.read", "message.write");
        // PORT???
    }

    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        endpoints.authenticationManager(authenticationManager).tokenStore(tokenStore)
                .userDetailsService(userDetailsService).accessTokenConverter(accessTokenConverter);
    }
}
