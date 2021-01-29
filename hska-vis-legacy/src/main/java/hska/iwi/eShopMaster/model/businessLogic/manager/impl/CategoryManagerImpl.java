package hska.iwi.eShopMaster.model.businessLogic.manager.impl;

import com.opensymphony.xwork2.ActionContext;
import hska.iwi.eShopMaster.model.businessLogic.manager.CategoryManager;
import hska.iwi.eShopMaster.model.Category;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class CategoryManagerImpl implements CategoryManager {
	private RestTemplate restTemplate = new RestTemplate();

	public CategoryManagerImpl() {
	}

	public List<Category> getCategories() {
		ResponseEntity<Category[]> response = this.restTemplate.exchange("http://zuul:8081/categories", HttpMethod.GET,
				getRequestEntity(), Category[].class);
		if (response.getStatusCode() == HttpStatus.OK) {
			return Arrays.asList(response.getBody());
		} else {
			return null;
		}
	}

	public Category getCategory(int id) {
		ResponseEntity<Category> response = this.restTemplate.exchange("http://zuul:8081/categories/" + id,
				HttpMethod.GET, getRequestEntity(), Category.class);
		if (response.getStatusCode() == HttpStatus.OK) {
			return response.getBody();
		} else {
			return null;
		}
	}

	// TODO
	public Category getCategoryByName(String name) {
		ResponseEntity<Category> response = this.restTemplate.exchange("http://zuul:8081/categories/name/" + name,
				HttpMethod.GET, getRequestEntity(), Category.class);
		if (response.getStatusCode() == HttpStatus.OK) {
			return response.getBody();
		} else {
			return null;
		}
	}

	public void addCategory(String name) {
		Category cat = new Category(name);
		ResponseEntity<Void> response = this.restTemplate.exchange("http://zuul:8081/categories", HttpMethod.POST,
				getRequestEntityWithBody(cat), Void.class);
	}

	public void delCategory(Category cat) { // this method is not needed
		ResponseEntity<Boolean> response = this.restTemplate.exchange("http://zuul:8081/categories/" + cat.getId(),
				HttpMethod.DELETE, getRequestEntity(), Boolean.class);
	}

	public void delCategoryById(int id) {
		ResponseEntity<Boolean> response = this.restTemplate.exchange("http://zuul:8081/categories/" + id,
				HttpMethod.DELETE, getRequestEntity(), Boolean.class);
	}

	public HttpEntity getRequestEntity() {
		Map<String, Object> session = ActionContext.getContext().getSession();
		HttpHeaders headers = new HttpHeaders();
		headers.add("Authorization", "bearer " + session.get("token"));
		return new HttpEntity<String>(headers);
	}

	public HttpEntity getRequestEntityWithBody(Category category) {
		Map<String, Object> session = ActionContext.getContext().getSession();
		HttpHeaders headers = new HttpHeaders();
		headers.add("Authorization", "bearer " + session.get("token"));
		return new HttpEntity(category, headers);
	}
}
