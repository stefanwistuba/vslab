package de.hska.iwi.vslab.productservice.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import de.hska.iwi.vslab.productservice.dao.ProductRepo;
import de.hska.iwi.vslab.productservice.model.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class ProductServiceController {

    @Autowired
    private ProductRepo repo;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public ResponseEntity<List<Product>> getProducts(
            @RequestParam(value = "searchString", required = false) String searchString,
            @RequestParam(value = "min", required = false) Double min,
            @RequestParam(value = "max", required = false) Double max,
            @RequestParam(value = "categoryId", required = false) Long categoryId) {
        Iterable<Product> productsList = repo.findAll();

        List<Product> products = new ArrayList<Product>();
        for (Product prod : productsList) {
            products.add(prod);
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

        if (categoryId != null) {
            products = products.stream().filter((Product product) -> {
                return product.categoryId == categoryId;
            }).collect(Collectors.toList());
        }

        return new ResponseEntity<List<Product>>(products, HttpStatus.OK);
    }

    @RequestMapping(value = "/", method = RequestMethod.POST)
    public ResponseEntity<Product> addProduct(@RequestBody Product product) {
        if (invalidProduct(product)) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        product = repo.save(product);

        return new ResponseEntity<Product>(product, HttpStatus.CREATED);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<Product> getProduct(@PathVariable(required = true, name = "id") long id) {
        Product product = repo.findById(id).get();
        return new ResponseEntity<Product>(product, HttpStatus.OK);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<?> deleteProduct(@PathVariable(required = true, name = "id") long id) {
        if (!repo.existsById(id)) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        Product product = repo.findById(id).get();
        repo.delete(product);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    private boolean invalidProduct(Product product) {
        if (product.name == null || product.name.length() == 0) {
            return true;
        }

        // Validate price:
        String productPrice = String.valueOf(product.price);

        if (productPrice.length() <= 0) {
            return true;
        }

        if (!productPrice.matches("[0-9]+(.[0-9][0-9]?)?") || Double.parseDouble(productPrice) < 0.0) {
            return true;
        }

        return false;
    }
}
