package ru.geekbrains.services.entities;

import ru.geekbrains.repos.entities.OrderItem;

public class OrderItemDAO {
    private Integer id;
    private OrderDAO order;
    private ProductDAO product;
    private Double price;
    private Integer quantity;

    public OrderItemDAO(OrderItem orderItem) {
        if(orderItem==null) return;
        this.id = orderItem.getId();
        OrderDAO orderDAO=new OrderDAO();
        orderDAO.setId(orderItem.getOrder().getId());
        this.order = orderDAO;
        this.product = new ProductDAO(orderItem.getProduct());
        this.price = orderItem.getPrice();
        this.quantity = orderItem.getQuantity();
    }

    public ProductDAO getProduct() {
        return product;
    }

    public void setProduct(ProductDAO product) {
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

    public OrderDAO getOrder() {
        return order;
    }

    public void setOrder(OrderDAO order) {
        this.order = order;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
