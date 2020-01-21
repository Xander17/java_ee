package ru.geekbrains.db;

public class QuantityProduct {

    private Product product;

    private Integer id;
    private String name;
    private String description;
    private Double price;
    private Integer quantity;

    public QuantityProduct(Product product, Integer quantity) {
        this.product = product;
        this.quantity = quantity;
    }

    public Integer getId() {
        return product.getId();
    }

    public void setId(Integer id) {
        product.setId(id);
    }

    public String getName() {
        return product.getName();
    }

    public void setName(String name) {
        product.setName(name);
    }

    public String getDescription() {
        return product.getDescription();
    }

    public void setDescription(String description) {
        product.setDescription(description);
    }

    public Double getPrice() {
        return product.getPrice();
    }

    public void setPrice(Double price) {
        product.setPrice(price);
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }
}
