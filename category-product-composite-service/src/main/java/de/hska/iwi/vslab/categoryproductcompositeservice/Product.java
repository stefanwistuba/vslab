package de.hska.iwi.vslab.categoryproductcompositeservice;

import de.hska.iwi.vslab.categoryproductcompositeservice.Category;
import java.io.Serializable;

public class Product implements Serializable {
    private static final long serialVersionUID = 1L;

    public Long id;
    public String name;
    public double price;
    public Long categoryId;
    public String details;
    public String categoryName;

    public Product() {

    }

    public Product(String name, double price, Long categoryId) {
        this.name = name;
        this.price = price;
        this.categoryId = categoryId;
    }

    public Product(String name, double price, Long categoryId, String details) {
        this.name = name;
        this.price = price;
        this.categoryId = categoryId;
        this.details = details;
    }

    public Long getId() {
        return id;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public Long getCategoryIy() {
        return categoryId;
    }

    public void setCategoryIy(Long categoryId) {
        this.categoryId = categoryId;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }
}
