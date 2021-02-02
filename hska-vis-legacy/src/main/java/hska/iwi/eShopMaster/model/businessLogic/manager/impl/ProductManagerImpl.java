package hska.iwi.eShopMaster.model.businessLogic.manager.impl;

import com.opensymphony.xwork2.ActionContext;
import hska.iwi.eShopMaster.model.businessLogic.manager.ProductManager;
import hska.iwi.eShopMaster.model.Product;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import java.util.*;

public class ProductManagerImpl implements ProductManager {
	private RestTemplate restTemplate = new RestTemplate();
	private final String productUrl = "http://zuul:8081/products";

	public ProductManagerImpl() {
	}

	public List<Product> getProducts() {
		ResponseEntity<Product[]> response = this.restTemplate.exchange(productUrl, HttpMethod.GET, getRequestEntity(),
				Product[].class);
		if (response.getStatusCode() == HttpStatus.OK) {
			return Arrays.asList(response.getBody());
		} else {
			return null;
		}
	}

	public List<Product> getProductsForSearchValues(String searchString, Double min, Double max) {
		String urlString = productUrl + "?";

		if (searchString != null) {
			urlString += "searchString=" + searchString + "&";
		}
		if (min != null) {
			urlString += "min=" + min + "&";
		}
		if (max != null) {
			urlString += "max=" + max;
		}

		ResponseEntity<Product[]> response = this.restTemplate.exchange(urlString, HttpMethod.GET, getRequestEntity(),
				Product[].class);
		if (response.getStatusCode() == HttpStatus.OK) {
			return Arrays.asList(response.getBody());
		} else {
			return null;
		}
	}

	public Product getProductById(Long id) {
		ResponseEntity<Product> response = this.restTemplate.exchange(productUrl + "/" + id, HttpMethod.GET,
				getRequestEntity(), Product.class);
		if (response.getStatusCode() == HttpStatus.OK) {
			return response.getBody();
		} else {
			return null;
		}
	}

	public Product getProductByName(String name) { // this method is not needed
		return new Product("Test", 10.0, 1L, "details");
	}

	public Long addProduct(String name, double price, Long categoryId, String details) {
		Long productId = -1L;

		Product product;
		if (details == null) {
			product = new Product(name, price, categoryId, "");
		} else {
			product = new Product(name, price, categoryId, details);
		}

		ResponseEntity<Product> response = this.restTemplate.exchange(productUrl, HttpMethod.POST,
				getRequestEntityWithBody(product), Product.class);
		if (response.getStatusCode() == HttpStatus.CREATED) {
			Product createdProduct = response.getBody();
			productId = createdProduct.getId();
		}

		return productId;
	}

	public boolean deleteProductById(Long id) {
		ResponseEntity<Boolean> response = this.restTemplate.exchange(productUrl + "/" + id, HttpMethod.DELETE,
				getRequestEntity(), Boolean.class);
		if (response.getStatusCode() == HttpStatus.OK) {
			if (response.getBody()) {
				return true;
			}
		}
		return false;
	}

	public boolean deleteProductsByCategoryId(int categoryId) {
		// TODO Auto-generated method stub
		return false;
	}

	public HttpEntity getRequestEntity() {
		Map<String, Object> session = ActionContext.getContext().getSession();
		HttpHeaders headers = new HttpHeaders();
		headers.add("Authorization", "bearer " + session.get("token"));
		return new HttpEntity<String>(headers);
	}

	public HttpEntity getRequestEntityWithBody(Product product) {
		Map<String, Object> session = ActionContext.getContext().getSession();
		HttpHeaders headers = new HttpHeaders();
		headers.add("Authorization", "bearer " + session.get("token"));
		return new HttpEntity(product, headers);
	}

}
