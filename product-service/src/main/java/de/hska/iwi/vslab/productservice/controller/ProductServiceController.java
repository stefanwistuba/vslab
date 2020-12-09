package de.hska.iwi.vslab.productservice.controller;

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
    public ResponseEntity<Iterable<Product>> getProducts() {
        Iterable<Product> products = repo.findAll();

        return new ResponseEntity<Iterable<Product>>(products, HttpStatus.OK);
    }

    @RequestMapping(value = "/", method = RequestMethod.POST)
    public ResponseEntity<?> addProduct(@RequestBody Product product) {
        if (invalidProduct(product)) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        product = repo.save(product);

        return new ResponseEntity<>(product.id, HttpStatus.CREATED);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<?> deleteProduct(@PathVariable(required = true, name = "id") long id) {
        Product product = repo.findById(id).get();
        if (product == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
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

        if (!productPrice.matches("[0-9]+(.[0-9][0-9]?)?")
                || Double.parseDouble(productPrice) < 0.0) {
            return true;
        }

        return false;
    }
}
