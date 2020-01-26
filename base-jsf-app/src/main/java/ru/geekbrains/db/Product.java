package ru.geekbrains.db;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "products")
public class Product implements Comparable<Product> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(length = 1024, nullable = false)
    private String name;
    @Column(length = 8096)
    private String description;
    @Column(nullable = false)
    private Double price;

    @ManyToOne
    private Category category;

    public Product() {
        category = new Category();
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

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return id.intValue() == product.id.intValue();
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public int compareTo(Product o) {
        return id - o.id;
    }
}
