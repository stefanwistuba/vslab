package de.hska.iwi.vslab.auth;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

import org.springframework.security.oauth2.client.OAuth2ClientContext;
import org.springframework.security.oauth2.client.OAuth2RestOperations;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;

import org.springframework.security.oauth2.client.resource.OAuth2ProtectedResourceDetails;
import org.springframework.security.oauth2.client.token.grant.client.ClientCredentialsResourceDetails;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableOAuth2Client;
import org.springframework.context.annotation.Configuration;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;


@Configuration
@EnableOAuth2Client
public class ClientConfig {

	@Bean
	@LoadBalanced
	public OAuth2RestTemplate restTemplate(
			@Qualifier("authServerClientCredentialsResourceDetails") OAuth2ProtectedResourceDetails resourceDetails) {
		return new OAuth2RestTemplate(resourceDetails);
	}


	@Bean
	protected OAuth2ProtectedResourceDetails authServerClientCredentialsResourceDetails() {
		ClientCredentialsResourceDetails resource = new ClientCredentialsResourceDetails();
		resource.setAccessTokenUri("http://auth:8088/oauth/token");
		resource.setClientId("auth-client");
		resource.setId("auth-client");
		resource.setClientSecret("secret");

		return resource;
	}

}
