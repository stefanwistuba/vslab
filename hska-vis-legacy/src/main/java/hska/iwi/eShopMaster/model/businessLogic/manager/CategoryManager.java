package hska.iwi.eShopMaster.model.businessLogic.manager;

import hska.iwi.eShopMaster.model.Category;
import org.springframework.http.HttpEntity;

import java.util.List;

public interface CategoryManager {

	public HttpEntity getRequestEntity();

	public List<Category> getCategories();

	public Category getCategory(int id);

	public Category getCategoryByName(String name);

	public void addCategory(String name);

	public void delCategory(Category cat);

	public void delCategoryById(int id);

}