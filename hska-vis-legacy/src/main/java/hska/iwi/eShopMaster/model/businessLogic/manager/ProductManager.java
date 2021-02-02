package hska.iwi.eShopMaster.model.businessLogic.manager;

import hska.iwi.eShopMaster.model.Product;
import org.springframework.http.HttpEntity;

import java.util.List;

public interface ProductManager {

	public HttpEntity getRequestEntity();

	public List<Product> getProducts();

	public Product getProductById(Long id);

	public Product getProductByName(String name);

	public Long addProduct(String name, double price, Long categoryId, String details);

	public List<Product> getProductsForSearchValues(String searchValue, Double searchMinPrice, Double searchMaxPrice);

	public boolean deleteProductsByCategoryId(int categoryId);

	public boolean deleteProductById(Long id);

}
