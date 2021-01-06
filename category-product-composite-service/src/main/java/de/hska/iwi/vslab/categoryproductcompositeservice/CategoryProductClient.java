package de.hska.iwi.vslab.categoryproductcompositeservice;

import org.springframework.web.client.RestTemplate;

// import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.client.RestTemplate;
import org.springframework.stereotype.Component;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;

import de.hska.iwi.vslab.categoryproductcompositeservice.Category;
import de.hska.iwi.vslab.categoryproductcompositeservice.Product;

@Component
public class CategoryProductClient {
    // private final Map<Long, Category> categoryCache = new LinkedHashMap<Long,
    // Category>();
    // private final Map<Long, Product> productCache = new LinkedHashMap<Long,
    // Product>();

    // private final Iterable<Category> categoriesCache;
    // private final Iterable<Product> productsCache;

    @Autowired
    private RestTemplate restTemplate;

    // public Product getProductCache(Long productId) {
    // return productCache.getOrDefault(productId, new Product());
    // }

    // public Iterable<Product> getProductsCache() {
    // return productsCache;
    // }

    // public Iterable<Category> getCategoriesCache() {
    // return categoriesCache;
    // }

    // public Category getCategoryCache(Long categoryId) {
    // return categoryCache.getOrDefault(categoryId, new Category());
    // }

    // @HystrixCommand(fallbackMethod = "getProductCache", commandProperties = {
    // @HystrixProperty(name = "circuitBreaker.requestVolumeThreshold", value = "2")
    // })
    public Product getProduct(Long productId) {
        Product pr = restTemplate.getForObject("http://product-service:8080/" + productId, Product.class);
        Category cat = getCategory(pr.categoryId);
        if (cat.getId() != null || cat.getName() != null) {
            pr.setCategoryName(cat.getName());
        }

        // productCache.putIfAbsent(productId, pr);
        return pr;
    }

    // @HystrixCommand(fallbackMethod = "getProductsCache", commandProperties = {
    // @HystrixProperty(name = "circuitBreaker.requestVolumeThreshold", value = "2")
    // })
    public List<Product> getProducts() {
        List<Product> tmpProducts = restTemplate.getForObject("http://product-service:8080/", List.class);
        tmpProducts.forEach(pr -> {
            Category cat = getCategory(pr.categoryId);
            if (cat.getId() != null || cat.getName() != null) {
                pr.setCategoryName(cat.getName());
            }
        });

        // productsCache = tmpProducts;
        return tmpProducts;
    }

    // @HystrixCommand(fallbackMethod = "getCategoryCache", commandProperties = {
    // @HystrixProperty(name = "circuitBreaker.requestVolumeThreshold", value = "2")
    // })
    public Category getCategory(Long categoryId) {
        Category tmpCategory = restTemplate.getForObject("http://category-service:8080/" + categoryId, Category.class);
        // categoryCache.putIfAbsent(categoryId, tmpCategory);
        return tmpCategory;
    }

    // @HystrixCommand(fallbackMethod = "getCategoriesCache", commandProperties = {
    // @HystrixProperty(name = "circuitBreaker.requestVolumeThreshold", value = "2")
    // })
    public List<Category> getCategories() {
        List<Category> tmpCategorys = restTemplate.getForObject("http://category-service:8080/", List.class);
        // categoriesCache = tmpCategorys;
        return tmpCategorys;
    }

    public void deleteProduct(Long productId) {
        try {
            restTemplate.delete("http://product-service:8080/" + productId);
        } catch (Exception e) {
            throw e;
        }
    }

    public String deleteCategory(Long categoryId) {
        try {
            List<Product> productList = restTemplate
                    .getForObject("http://product-service:8080/?categoryId=" + categoryId, List.class);

            if (productList.size() > 0) {
                return null;
            }

            restTemplate.delete("http://category-service:8080/" + categoryId);
            return "deleted";
        } catch (Exception e) {
            throw e;
        }
    }

    public Category addCategory(Category category) {
        Category tmpCategory = restTemplate.postForObject("http://category-service:8080/", category, Category.class);
        return tmpCategory;
    }

    public Product addProduct(Product product) {
        Category cat = getCategory(product.categoryId);
        if (cat == null) {
            return null;
        }
        Product tmpProduct = restTemplate.postForObject("http://product-service:8080/", product, Product.class);
        return tmpProduct;
    }

}