package de.hska.iwi.vslab.categoryproductcompositeservice;

import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.client.RestTemplate;
import org.springframework.stereotype.Component;
import org.springframework.http.ResponseEntity;
import java.util.stream.Collectors;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;

import de.hska.iwi.vslab.categoryproductcompositeservice.Category;
import de.hska.iwi.vslab.categoryproductcompositeservice.Product;

@Component
public class CategoryProductClient {
    private final Map<Long, Category> categoryCache = new LinkedHashMap<Long, Category>();
    private final Map<Long, Product> productCache = new LinkedHashMap<Long, Product>();

    @Autowired
    private RestTemplate restTemplate;

    public Product getProductCache(Long productId) {
        return productCache.getOrDefault(productId, new Product());
    }

    public Product[] getProductsCache(String searchString, Double min, Double max) {
        List<Product> products = new ArrayList<Product>();
        for (Product pr : productCache.values()) {
            products.add(pr);
        }

        if (searchString != null) {
            products = products.stream().filter((Product p) -> {
                return p.name.contains(searchString);
            }).collect(Collectors.toList());
        }
        if (min != null) {
            products = products.stream().filter((Product product) -> {
                return product.price >= min;
            }).collect(Collectors.toList());
        }
        if (max != null) {
            products = products.stream().filter((Product product) -> {
                return product.price <= max;
            }).collect(Collectors.toList());
        }

        return products.toArray(new Product[0]);
    }

    public Category getCategoryCache(Long categoryId) {
        return categoryCache.getOrDefault(categoryId, new Category());
    }

    public Category[] getCategoriesCache() {
        List<Category> categories = new ArrayList<Category>();
        for (Category cat : categoryCache.values()) {
            categories.add(cat);
        }
        return categories.toArray(new Category[0]);
    }

    @HystrixCommand(fallbackMethod = "getProductCache", commandProperties = {
            @HystrixProperty(name = "circuitBreaker.requestVolumeThreshold", value = "2") })
    public Product getProduct(Long productId) {
        Product pr = restTemplate.getForObject("http://product-service:8080/" + productId, Product.class);
        Category cat = getCategory(pr.categoryId);
        if (cat.getId() != null || cat.getName() != null) {
            pr.setCategoryName(cat.getName());
        }

        productCache.putIfAbsent(productId, pr);
        return pr;
    }

    @HystrixCommand(fallbackMethod = "getProductsCache", commandProperties = {
            @HystrixProperty(name = "circuitBreaker.requestVolumeThreshold", value = "2") })
    public Product[] getProducts(String searchString, Double min, Double max) {
        String urlString = "http://product-service:8080/?";

        if (searchString != null) {
            urlString += "searchString=" + searchString + "&";
        }
        if (min != null) {
            urlString += "min=" + min + "&";
        }
        if (max != null) {
            urlString += "max=" + max;
        }

        ResponseEntity<Product[]> response = restTemplate.getForEntity(urlString, Product[].class);
        Product[] products = response.getBody();

        productCache.clear();

        for (Product pr : products) {
            Category cat = getCategory(pr.categoryId);
            if (cat.getId() != null || cat.getName() != null) {
                pr.setCategoryName(cat.getName());
            }
            productCache.put(pr.getId(), pr);
        }

        return products;
    }

    @HystrixCommand(fallbackMethod = "getCategoryCache", commandProperties = {
            @HystrixProperty(name = "circuitBreaker.requestVolumeThreshold", value = "2") })
    public Category getCategory(Long categoryId) {
        Category tmpCategory = restTemplate.getForObject("http://category-service:8080/" + categoryId, Category.class);
        categoryCache.putIfAbsent(categoryId, tmpCategory);
        return tmpCategory;
    }

    @HystrixCommand(fallbackMethod = "getCategoriesCache", commandProperties = {
            @HystrixProperty(name = "circuitBreaker.requestVolumeThreshold", value = "2") })
    public Category[] getCategories() {
        ResponseEntity<Category[]> response = restTemplate.getForEntity("http://category-service:8080/",
                Category[].class);

        Category[] categories = response.getBody();

        categoryCache.clear();
        for (Category category : categories) {
            categoryCache.put(category.getId(), category);
        }
        return categories;
    }

    public void deleteProduct(Long productId) {
        try {
            restTemplate.delete("http://product-service:8080/" + productId);

            productCache.remove(productId);
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
            categoryCache.remove(categoryId);
            return "deleted";
        } catch (Exception e) {
            throw e;
        }
    }

    public Category addCategory(Category category) {
        Category tmpCategory = restTemplate.postForObject("http://category-service:8080/", category, Category.class);
        categoryCache.put(category.getId(), category);
        return tmpCategory;
    }

    public Product addProduct(Product product) {
        try {
            Category cat = getCategory(product.categoryId);
            Product tmpProduct = restTemplate.postForObject("http://product-service:8080/", product, Product.class);
            product.setCategoryName(cat.getName());
            productCache.put(product.getId(), product);
            return tmpProduct;
        } catch (Exception e) {
            throw e;
        }
    }

}