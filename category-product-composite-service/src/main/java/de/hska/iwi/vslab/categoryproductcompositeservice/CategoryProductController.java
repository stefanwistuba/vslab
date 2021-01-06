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
    public ResponseEntity<List<Product>> getProducts() {
        List<Product> allProducts = client.getProducts();
        return new ResponseEntity<>(allProducts, HttpStatus.OK);
    }

    @RequestMapping(value = "/categories", method = RequestMethod.POST)
    public ResponseEntity<?> addCategory(@RequestBody Category category) {
        category = client.addCategory(category);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @RequestMapping(value = "/products", method = RequestMethod.POST)
    public ResponseEntity<?> addProduct(@RequestBody Product product) {
        product = client.addProduct(product);
        if (product == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @RequestMapping(value = "/products/{productID}", method = RequestMethod.GET)
    public ResponseEntity<Product> getProduct(@PathVariable Long productID) {
        Product result = client.getProduct(productID);
        return new ResponseEntity<Product>(result, HttpStatus.OK);
    }

    @RequestMapping(value = "/products/{productID}", method = RequestMethod.DELETE)
    public ResponseEntity<Category> deleteProduct(@PathVariable Long productID) {
        client.deleteProduct(productID);
        return new ResponseEntity<>(null, HttpStatus.OK);
    }

    @RequestMapping(value = "/categories", method = RequestMethod.GET)
    public ResponseEntity<List<Category>> getCategories() {
        List<Category> allCategories = client.getCategories();
        return new ResponseEntity<>(allCategories, HttpStatus.OK);
    }

    @RequestMapping(value = "/categories/{categoryID}", method = RequestMethod.DELETE)
    public ResponseEntity<Category> deleteCategory(@PathVariable Long categoryID) {
        String result = client.deleteCategory(categoryID);
        if (result == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(null, HttpStatus.OK);
    }
}
