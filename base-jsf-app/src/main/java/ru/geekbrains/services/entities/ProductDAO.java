package ru.geekbrains.services.entities;

import ru.geekbrains.repos.entities.Product;

import java.util.Objects;

public class ProductDAO implements Comparable<ProductDAO> {
    private Integer id;
    private String name;
    private String description;
    private Double price;
    private CategoryDAO category;

    public ProductDAO(Product product) {
        if(product==null) return;
        this.id = product.getId();
        this.name = product.getName();
        this.description = product.getDescription();
        this.price = product.getPrice();
        this.category = new CategoryDAO(product.getCategory());
    }

    public ProductDAO() {
        this.category = new CategoryDAO();
    }

    public Integer getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public CategoryDAO getCategory() {
        return category;
    }

    public void setCategory(CategoryDAO category) {
        this.category = category;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProductDAO product = (ProductDAO) o;
        return id.intValue() == product.id.intValue();
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public int compareTo(ProductDAO o) {
        return id - o.id;
    }
}
