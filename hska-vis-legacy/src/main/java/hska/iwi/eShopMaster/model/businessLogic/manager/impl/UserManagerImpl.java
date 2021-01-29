package hska.iwi.eShopMaster.model.businessLogic.manager.impl;

import com.opensymphony.xwork2.ActionContext;
import hska.iwi.eShopMaster.model.businessLogic.manager.UserManager;
import hska.iwi.eShopMaster.model.User;
import org.springframework.http.*;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.security.oauth2.client.token.grant.password.ResourceOwnerPasswordResourceDetails;
import org.springframework.web.client.RestTemplate;
import java.util.Arrays;
import java.util.Map;

/**
 * 
 * @author knad0001
 */

public class UserManagerImpl implements UserManager {
	private OAuth2RestTemplate webshopAuthTemplate;
	private RestTemplate restTemplate = new RestTemplate();

	public UserManagerImpl() {
	}

	public String authorizeUser(String username, String password) {
		if (username == null || username.equals("")) {
			return null;
		}
		if (password == null || password.equals("")) {
			return null;
		}

		ResourceOwnerPasswordResourceDetails resourceDetails = new ResourceOwnerPasswordResourceDetails();
		resourceDetails.setUsername(username);
		resourceDetails.setPassword(password);
		resourceDetails.setAccessTokenUri("http://zuul:8081/auth/oauth/token");
		resourceDetails.setClientId("webshop-client");
		resourceDetails.setClientSecret("strong");
		resourceDetails.setGrantType("password");
		resourceDetails.setScope(Arrays.asList("read", "write"));
		this.webshopAuthTemplate = new OAuth2RestTemplate(resourceDetails);

		return this.webshopAuthTemplate.getAccessToken().toString();
	}

	public boolean registerUser(String username, String name, String lastname, String password, Long role) {
		User user = new User(name, lastname, username, password, role);
		ResponseEntity<Void> response = this.restTemplate.postForEntity("http://zuul:8081/users", user, Void.class);
		if (response.getStatusCode() == HttpStatus.CREATED) {
			return true;
		}
		return false;
	}

	public User getUserByUsername(String username) {
		ResponseEntity<User> response = this.restTemplate.exchange("http://zuul:8081/users/" + username, HttpMethod.GET,
				getRequestEntity(), User.class);
		if (response.getStatusCode() == HttpStatus.OK) {
			return response.getBody();
		} else {
			return null;
		}
	}

	public boolean deleteUserById(Long id) {
		ResponseEntity<Boolean> response = this.restTemplate.exchange("http://zuul:8081/users/" + id, HttpMethod.DELETE,
				getRequestEntity(), Boolean.class);
		if (response.getStatusCode() == HttpStatus.OK) {
			if (response.getBody()) {
				return true;
			}
		}
		return false;
	}

	public boolean doesUserAlreadyExist(String username) {
		ResponseEntity<User> response = this.restTemplate.exchange("http://zuul:8081/users/" + username, HttpMethod.GET,
				getRequestEntity(), User.class);
		if (response.getStatusCode() == HttpStatus.OK) {
			if (response.getBody() != null) {
				return true;
			}
		}
		return false;
	}

	public boolean validate(User user) {
		if (user.getFirstName() == null || user.getPassword() == null || user.getRole() == null
				|| user.getLastName() == null || user.getUserName() == null) {
			return false;
		}

		return true;
	}

	public HttpEntity getRequestEntity() {
		Map<String, Object> session = ActionContext.getContext().getSession();
		HttpHeaders headers = new HttpHeaders();
		headers.add("Authorization", "bearer " + session.get("token"));
		return new HttpEntity<String>(headers);
	}

}
