package hska.iwi.eShopMaster.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.security.oauth2.client.resource.OAuth2ProtectedResourceDetails;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableOAuth2Client;

@SuppressWarnings("deprecation")
@Configuration
@EnableOAuth2Client
public class OAuth2ClientConfig {
    @Bean
    public OAuth2RestTemplate webshopClientPasswordRestTemplate(OAuth2ProtectedResourceDetails resourceDetails
    ) {
        return new OAuth2RestTemplate(resourceDetails);
    }
}