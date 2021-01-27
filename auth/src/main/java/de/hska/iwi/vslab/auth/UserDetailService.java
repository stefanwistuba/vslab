package de.hska.iwi.vslab.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.client.RestTemplate;
import de.hska.iwi.vslab.auth.User;

public class UserDetailService implements UserDetailsService {

    @Autowired
    private RestTemplate restTemplate;


    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = restTemplate.getForObject("http://user-role-service:8080/" + username, User.class);
        if (user == null) {
            throw new UsernameNotFoundException("User " + username + " not found.");
        }

        return user;
    }
}
