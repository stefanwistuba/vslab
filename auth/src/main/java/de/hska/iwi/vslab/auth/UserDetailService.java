package de.hska.iwi.vslab.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import de.hska.iwi.vslab.auth.User;
import org.springframework.security.oauth2.client.OAuth2ClientContext;
import org.springframework.security.oauth2.client.OAuth2RestOperations;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.security.oauth2.client.resource.OAuth2ProtectedResourceDetails;
import org.springframework.security.oauth2.client.token.grant.client.ClientCredentialsResourceDetails;
import org.springframework.context.annotation.Bean;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableOAuth2Client;

@EnableOAuth2Client
public class UserDetailService implements UserDetailsService {

    @Autowired
    @Qualifier("restTemplate")
    private OAuth2RestTemplate restTemplate;

    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = restTemplate.getForObject("http://user-role-service:8080/" + username, User.class);
        if (user == null) {
            throw new UsernameNotFoundException("User " + username + " not found.");
        }

        // user.setPassword("{noop}" + user.getPassword());

        return new org.springframework.security.core.userdetails.User(user.getUserName(), "{noop}" + user.getPassword(),
                user.getAuthorities());
    }
}
