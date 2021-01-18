package de.hska.iwi.vslab.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;
import com.nimbusds.jose.jwk.JWKSet;

@RestController
public class AuthController {

    @Autowired
    @Qualifier("jwkSet")
    private JWKSet jwkSet;

    @GetMapping(value = "/oauth2/keys", produces = "application/json; charset=UTF-8")
    public String keys() {
        return this.jwkSet.toString();
    }
}
