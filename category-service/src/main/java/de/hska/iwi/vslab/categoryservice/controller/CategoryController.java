package de.hska.iwi.vslab.categoryservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import java.util.List;
import java.util.ArrayList;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import de.hska.iwi.vslab.categoryservice.dao.CategoryRepository;
import de.hska.iwi.vslab.categoryservice.model.Category;

@RestController
public class CategoryController {
    @Autowired
    private CategoryRepository repo;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public ResponseEntity<Category[]> getCategories() {
        Iterable<Category> allPolls = repo.findAll();

        List<Category> categories = new ArrayList<Category>();
        for (Category cat : allPolls) {
            categories.add(cat);
        }

        Category[] categoriesArray = categories.toArray(new Category[0]);

        return new ResponseEntity<Category[]>(categoriesArray, HttpStatus.OK);
    }

    @RequestMapping(value = "/", method = RequestMethod.POST)
    public ResponseEntity<?> addCategory(@RequestBody Category category) {
        category = repo.save(category);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @RequestMapping(value = "/{categoryID}", method = RequestMethod.GET)
    public ResponseEntity<Category> getCategory(@PathVariable Long categoryID) {
        if (!repo.existsById(categoryID)) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        Category category = repo.findById(categoryID).get();
        return new ResponseEntity<>(category, HttpStatus.OK);
    }

    @RequestMapping(value = "/{categoryID}", method = RequestMethod.DELETE)
    public ResponseEntity<Category> deleteCategory(@PathVariable Long categoryID) {
        if (!repo.existsById(categoryID)) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        Category category = repo.findById(categoryID).get();
        repo.delete(category);
        return new ResponseEntity<>(null, HttpStatus.OK);
    }

}