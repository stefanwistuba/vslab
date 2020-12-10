package de.hska.iwi.vslab.productservice.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;

@Entity
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public Long id;
    public String name;
    public double price;
    public Long categoryId;
    public String details;

    public Product(String name, double price, long categoryId) {
        this.name = name;
        this.price = price;
        this.categoryId = categoryId;
    }

    public Product() {

    }

    public Product(String name, double price, long categoryId, String details) {
        this.name = name;
        this.price = price;
        this.categoryId = categoryId;
        this.details = details;
    }
}
