package de.hska.iwi.vslab.categoryproductcompositeservice;

import de.hska.iwi.vslab.categoryproductcompositeservice.Category;

public class Product {
    public Long id;
    public String name;
    public double price;
    public Long categoryId;
    public String details;
    public Category category;

    public Product() {

    }

    public Product(String name, double price, long categoryId) {
        this.name = name;
        this.price = price;
        this.categoryId = categoryId;
    }

    public Product(String name, double price, Category category) {
        this.name = name;
        this.price = price;
        this.category = category;
    }

    public Product(String name, double price, long categoryId, String details) {
        this.name = name;
        this.price = price;
        this.categoryId = categoryId;
        this.details = details;
    }
}
