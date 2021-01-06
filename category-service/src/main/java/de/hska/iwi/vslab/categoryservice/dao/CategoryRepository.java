package de.hska.iwi.vslab.categoryservice.dao;

import de.hska.iwi.vslab.categoryservice.model.Category;
import org.springframework.data.repository.CrudRepository;

public interface CategoryRepository extends CrudRepository<Category, Long> {
}