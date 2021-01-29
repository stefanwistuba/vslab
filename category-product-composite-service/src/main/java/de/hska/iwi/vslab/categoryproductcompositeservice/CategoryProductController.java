package de.hska.iwi.vslab.categoryproductcompositeservice;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class CategoryProductController {

    @Autowired
    private CategoryProductClient client;

    @RequestMapping(value = "/products", method = RequestMethod.GET)
    public ResponseEntity<Product[]> getProducts(
            @RequestParam(value = "searchString", required = false) String searchString,
            @RequestParam(value = "min", required = false) Double min,
            @RequestParam(value = "max", required = false) Double max) {
        Product[] allProducts = client.getProducts(searchString, min, max);
        return new ResponseEntity<Product[]>(allProducts, HttpStatus.OK);
    }

    @RequestMapping(value = "/categories", method = RequestMethod.POST)
    public ResponseEntity<?> addCategory(@RequestBody Category category) {
        category = client.addCategory(category);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @RequestMapping(value = "/products", method = RequestMethod.POST)
    public ResponseEntity<?> addProduct(@RequestBody Product product) {
        try {
            product = client.addProduct(product);
            return new ResponseEntity<>(product, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping(value = "/products/{productID}", method = RequestMethod.GET)
    public ResponseEntity<Product> getProduct(@PathVariable Long productID) {
        Product result = client.getProduct(productID);
        return new ResponseEntity<Product>(result, HttpStatus.OK);
    }

    @RequestMapping(value = "/products/{productID}", method = RequestMethod.DELETE)
    public ResponseEntity<Category> deleteProduct(@PathVariable Long productID) {
        try {
            client.deleteProduct(productID);
            return new ResponseEntity<>(null, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @RequestMapping(value = "/categories", method = RequestMethod.GET)
    public ResponseEntity<Category[]> getCategories() {
        Category[] allCategories = client.getCategories();
        return new ResponseEntity<>(allCategories, HttpStatus.OK);
    }

    @RequestMapping(value = "/categories/{categoryId}", method = RequestMethod.GET)
    public ResponseEntity<Category> getCategory(@PathVariable Long categoryId) {
        Category result = client.getCategory(categoryId);
        return new ResponseEntity<Category>(result, HttpStatus.OK);
    }

    @RequestMapping(value = "/categories/{categoryID}", method = RequestMethod.DELETE)
    public ResponseEntity<Category> deleteCategory(@PathVariable Long categoryID) {
        try {
            String result = client.deleteCategory(categoryID);
            if (result == null) {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
            return new ResponseEntity<>(null, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
