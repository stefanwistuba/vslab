package de.hska.iwi.vslab.productservice.dao;

import de.hska.iwi.vslab.productservice.model.Product;
import org.springframework.data.repository.CrudRepository;

public interface ProductRepo extends CrudRepository<Product, Long> {}
