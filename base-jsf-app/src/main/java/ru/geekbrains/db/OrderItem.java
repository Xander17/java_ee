package ru.geekbrains.db;

public class OrderItem {
    private Product product;
    private Double price;
    private Integer quantity;
    //private Double sum;

    public OrderItem(Product product, Double price, Integer quantity) {
        this.product = product;
        this.price = price;
        this.quantity = quantity;
        //setSum(null);
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

//    public Double getSum() {
//        return sum;
//    }
//
//    public void setSum(Double sum) {
//        this.sum = Math.round(price * quantity * 100) / 100.;
//    }
}
