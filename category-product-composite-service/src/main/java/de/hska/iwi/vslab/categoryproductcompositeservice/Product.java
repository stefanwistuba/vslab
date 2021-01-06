package de.hska.iwi.vslab.categoryproductcompositeservice;

public class Product {

    public Long id;

    public String name;
    public Long price;
    public Long categoryId;
    public String details;
    public String categoryName;

    public Long getId() {
        return id;
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

    public Long getPrice() {
        return price;
    }

    public void setPrice(Long price) {
        this.price = price;
    }

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public String toString() {
        return ("id: " + this.id + " name: " + this.name + " price: " + this.price + " category: " + this.categoryId
                + " details: " + this.details);
    }

    public void setCategoryName(String name) {
        this.categoryName = name;
    }

    public String getCategoryName() {
        return categoryName;
    }
}
