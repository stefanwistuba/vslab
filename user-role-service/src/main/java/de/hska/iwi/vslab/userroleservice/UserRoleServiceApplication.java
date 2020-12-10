package de.hska.iwi.vslab.userroleservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class UserRoleServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(UserRoleServiceApplication.class, args);
	}

}
